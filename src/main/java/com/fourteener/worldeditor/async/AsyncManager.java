package com.fourteener.worldeditor.async;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.fourteener.worldeditor.blockiterator.BlockIterator;
import com.fourteener.worldeditor.main.GlobalVars;
import com.fourteener.worldeditor.main.Main;
import com.fourteener.worldeditor.main.NBTExtractor;
import com.fourteener.worldeditor.main.SetBlock;
import com.fourteener.worldeditor.operations.Operator;
import com.fourteener.worldeditor.selection.Clipboard;
import com.fourteener.worldeditor.undo.UndoManager;

public class AsyncManager {

	// NBT extractor for clipboard
	NBTExtractor nbtExtractor = new NBTExtractor();
	
	// Store operations
	private ArrayDeque<AsyncOperation> operations = new ArrayDeque<AsyncOperation>();
	
	// Store large operations
	private Queue<AsyncOperation> largeOps = new ArrayDeque<AsyncOperation>();
	
	// On initialization start the scheduler
	public AsyncManager () {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(GlobalVars.plugin, new Runnable () {
			@Override
			public void run () {
				GlobalVars.asyncManager.performOperation();
			}
		} , GlobalVars.ticksPerAsync, GlobalVars.ticksPerAsync);
	}
	
	// TODO add a command to call this
	// Drops the head of the async queue
	public void dropAsync () {
		operations.remove();
	}
	
	// TODO add a command that calls this
	// About how big is the async queue?
	public void asyncProgress (Player p) {
		long remBlocks = 0;
		for (AsyncOperation a : operations) {
			if (a.blocks != null) {
				remBlocks += a.blocks.getRemainingBlocks();
			}
			else if (a.toOperate != null) {
				remBlocks += a.toOperate.size();
			}
			else {
				remBlocks += 100;
			}
		}
		int remTime = (int) (((double) remBlocks) / (GlobalVars.blocksPerAsync * (20.0 / GlobalVars.ticksPerAsync)));
		p.sendMessage("§aThere are " + remBlocks + " blocks in the async queue, for an estimated remaining time of " + remTime + " seconds.");
	}
	
	// Schedule a block iterator task
	public void scheduleEdit (Operator o, Player p, BlockIterator b) {
		if (p == null) {
			operations.add(new AsyncOperation(o, b));
		}
		else if (b.getTotalBlocks() > GlobalVars.undoLimit) {
			largeOps.add(new AsyncOperation(o, p, b));
			p.sendMessage("§aThis is a large edit that cannot be undone, and may stall 14erEdit for a while.");
			p.sendMessage("§aPlease type §b/confirm §ato confirm or §b/cancel §a to cancel");
			
		}
		else {
			operations.add(new AsyncOperation(true, p));
			operations.add(new AsyncOperation(o, p, b));
			operations.add(new AsyncOperation(false, p));
		}
	}

	public void scheduleEdit (Operator o, BlockIterator b) {
		operations.add(new AsyncOperation(o, b));
	}
	
	public void scheduleEdit (BlockIterator b, Player p, String path) {
		largeOps.add(new AsyncOperation(b, p, path));
		p.sendMessage("§aThis is a large edit that cannot be undone, and may stall 14erEdit for a while.");
		p.sendMessage("§aPlease type §b/confirm §ato confirm or §b/cancel §a to cancel");
	}
	
	public void scheduleEdit (Player p, String path) {
		largeOps.add(new AsyncOperation(p, path));
		p.sendMessage("§aThis is a large edit that cannot be undone, and may stall 14erEdit for a while.");
		p.sendMessage("§aPlease type §b/confirm §ato confirm or §b/cancel §a to cancel");
	}
	
	public void scheduleEdit (Clipboard c, ArrayDeque<Block> b, boolean save) {
		if (b.size() > GlobalVars.undoLimit) {
			largeOps.add(new AsyncOperation(c, b, save));
			c.owner.sendMessage("§aThis is a large edit that cannot be undone, and may stall 14erEdit for a while.");
			c.owner.sendMessage("§aPlease type §b/confirm §ato confirm or §b/cancel §a to cancel");
			
		}
		else {
			operations.add(new AsyncOperation(true, c.owner));
			operations.add(new AsyncOperation(c, b, save));
			operations.add(new AsyncOperation(false, c.owner));
		}
	}
	
	public void scheduleEdit (Clipboard c, List<Block> b, boolean save) {
		ArrayDeque<Block> d = new ArrayDeque<Block>();
		d.addAll(b);
		scheduleEdit(c, d, save);
	}
	
	// Confirm large edits
	public void confirmEdits (int number) {
		number = number < 1 ? 1 : number > largeOps.size() ? largeOps.size() : number;
		if (number < largeOps.size()) return;
		while (number-- > 0) {
			operations.add(largeOps.remove());
		}
	}
	
	// Cancel large edits
	public void cancelEdits (int number) {
		number = number < 1 ? 1 : number > largeOps.size() ? largeOps.size() : number;
		if (number < largeOps.size()) return;
		while (number-- > 0) {
			largeOps.remove();
		}
	}
	
	// Scheduled task to operate
	public void performOperation () {
		// If there isn't anything to do, return
		if (operations.size() == 0)
			return;
		
		// Limit operations per run
		long doneOperations = 0;
		
		// Loop until finished
		while (doneOperations < GlobalVars.blocksPerAsync && operations.size() > 0) {
			AsyncOperation op = operations.peek();
			if (op.key.equalsIgnoreCase("startundo")) {
				Main.logDebug("Async undo started for player " + op.player.getName());
				GlobalVars.currentUndo = UndoManager.getUndo(op.player);
				GlobalVars.currentUndo.startUndo();
				operations.remove();
				doneOperations += 100;
			}
			else if (op.key.equalsIgnoreCase("finishundo")) {
				GlobalVars.currentUndo.finishUndo();
				GlobalVars.currentUndo = null;
				Main.logDebug("Async undo finished for player " + op.player.getName());
				operations.remove();
				doneOperations += 100;
			}
			else if (op.key.equalsIgnoreCase("iteredit")) {
				Main.logDebug("Started async iter edit for player " + op.player.getName());
				Block b = null;
				while (doneOperations < GlobalVars.blocksPerAsync) {
					b = op.blocks.getNext();
					op.operation.operateOnBlock(b, op.player);
					doneOperations++;
				}
				if (b == null) {
					operations.remove();
				}
			}
			else if (op.key.equalsIgnoreCase("rawiteredit")) {
				Main.logDebug("Started async raw iter edit for player " + op.player.getName());
				Block b = null;
				while (doneOperations < GlobalVars.blocksPerAsync && op.toOperate.size() > 0) {
					b = op.blocks.getNext();
					op.operation.operateOnBlock(b);
					doneOperations++;
				}
				if (b == null) {
					operations.remove();
				}
			}
			else if (op.key.equalsIgnoreCase("messyedit")) {
				Main.logDebug("Async messy edit started for player " + op.player.getName());
				operations.remove().operation.messyOperate();
				doneOperations += 100;
			}
			else if (op.key.equalsIgnoreCase("edit")) {
				Main.logDebug("Started async edit for player " + op.player.getName());
				while (doneOperations < GlobalVars.blocksPerAsync && op.toOperate.size() > 0) {
					Block b = op.toOperate.removeFirst();
					op.operation.operateOnBlock(b, op.player);
					doneOperations++;
				}
				if (op.toOperate.size() == 0) {
					operations.remove();
				}
			}
			else if (op.key.equalsIgnoreCase("rawedit")) {
				Main.logDebug("Started async raw edit for player " + op.player.getName());
				while (doneOperations < GlobalVars.blocksPerAsync && op.toOperate.size() > 0) {
					Block b = op.toOperate.removeFirst();
					op.operation.operateOnBlock(b);
					doneOperations++;
				}
				if (op.toOperate.size() == 0) {
					operations.remove();
				}
			}
			else if (op.key.equalsIgnoreCase("saveclip")) {
				Main.logDebug("Started async copy for player " + op.clipboard.owner.getName());
				while (doneOperations < GlobalVars.blocksPerAsync && op.toOperate.size() > 0) {
					Block b = op.toOperate.removeFirst();
					int xB = Math.abs(b.getX() - op.clipboard.xNeg);
					int yB = Math.abs(b.getY() - op.clipboard.yNeg);
					int zB = Math.abs(b.getZ() - op.clipboard.zNeg);
					op.clipboard.blockData.set(xB + (zB * op.clipboard.width) + (yB * op.clipboard.length * op.clipboard.width), b.getBlockData().getAsString());
					op.clipboard.nbtData.set(xB + (zB * op.clipboard.width) + (yB * op.clipboard.length * op.clipboard.width), nbtExtractor.getNBT(b.getState()));
					doneOperations++;
				}
				if (op.toOperate.size() == 0) {
					op.clipboard.owner.sendMessage("§dSelection copied");
					operations.remove();
				}
			}
			else if (op.key.equalsIgnoreCase("loadclip")) {
				Main.logDebug("Started async paste for player " + op.clipboard.owner.getName());
				while (doneOperations < GlobalVars.blocksPerAsync && op.toOperate.size() > 0) {
					Block b = op.toOperate.removeFirst();
					Material blockMat = Material.matchMaterial(op.clipboard.blockData.get(op.clipboard.loadPos).split("\\[")[0]);
					BlockData blockDat = Bukkit.getServer().createBlockData(op.clipboard.blockData.get(op.clipboard.loadPos));
					String nbt = op.clipboard.nbtData.get(op.clipboard.loadPos);
					// Set the block
					if (blockMat == Material.AIR) {
						if (op.clipboard.setAir) {
							SetBlock.setMaterial(b, blockMat);
							b.setBlockData(blockDat);
							if (!nbt.equalsIgnoreCase("")) {
								String command = "data merge block " + b.getX() + " " + b.getY() + " " + b.getZ() + " " + nbt;
								Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
							}
						}
					}
					else {
						SetBlock.setMaterial(b, blockMat);
						b.setBlockData(blockDat);
						if (!nbt.equalsIgnoreCase("")) {
							String command = "data merge block " + b.getX() + " " + b.getY() + " " + b.getZ() + " " + nbt;
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
						}
					}
					op.clipboard.loadPos++;
					doneOperations++;
				}
				if (op.toOperate.size() == 0) {
					op.clipboard.owner.sendMessage("§dSelection pasted");
					operations.remove();
				}
			}
			// TODO: saveschem
			// TODO: loadschem
			else {
				operations.remove();
			}
		}
		
		// Debug message
		if (doneOperations > 0) {
			Main.logDebug("Performed " + doneOperations + " async operations");
		}
	}
}
