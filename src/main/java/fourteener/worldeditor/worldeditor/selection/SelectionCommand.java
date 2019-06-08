package fourteener.worldeditor.worldeditor.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fourteener.worldeditor.main.Main;
import fourteener.worldeditor.operations.Operator;
import fourteener.worldeditor.worldeditor.undo.UndoElement;
import fourteener.worldeditor.worldeditor.undo.UndoManager;

public class SelectionCommand {
	public static boolean performCommand (String[] args, Player player) {
		// First, get the wand that this player owns
		SelectionWand wand = null;
		for (SelectionWand s : SelectionWandListener.wands) {
			if (s.owner.equals(player)) {
				wand = s;
				break;
			}
		}
		if (wand == null && args[2].equalsIgnoreCase("load") && (args[1].equalsIgnoreCase("schematic") || args[1].equalsIgnoreCase("schem"))) {
			SelectionWand newWand = (SelectionWand.giveNewWand((player).getPlayer()));
			SelectionWandListener.wands.add(newWand);
			wand = newWand;
		}
		if (wand == null) {
			return false;
		}
		
		// Then get the applicable wand manager
		SelectionManager manager = wand.manager;
		
		// Perform an operation
		if (args[1].equalsIgnoreCase("op")) {
			return operate(manager, wand, args);
		}
		
		// Expand a selection
		else if (args[1].equalsIgnoreCase("expand")) {
			return expand(manager, Double.parseDouble(args[2]), args[3], wand.owner);
		}
		
		// Copy a selection
		else if (args[1].equalsIgnoreCase("copy")) {
			double[] pos1 = manager.getMostNegativeCorner();
			double[] pos2 = manager.getMostPositiveCorner();
			List<Block> blockArray = new ArrayList<Block>();
			for (int x = (int) pos1[0]; x <= pos2[0]; x++) {
				for (int y = (int) pos1[1]; y <= pos2[1]; y++) {
					for (int z = (int) pos1[2]; z <= pos2[2]; z++) {
						blockArray.add(Main.world.getBlockAt(x, y, z));
					}
				}
			}
			return ClipboardManager.getClipboard(wand.owner).saveToClipboard(
					wand.owner.getLocation().getBlockX(),
					wand.owner.getLocation().getBlockY(),
					wand.owner.getLocation().getBlockZ(), blockArray);
		}
		
		// Paste a selection
		else if (args[1].equalsIgnoreCase("paste")) {
			return ClipboardManager.getClipboard(wand.owner).pasteClipboard(
					wand.owner.getLocation().getBlockX(),
					wand.owner.getLocation().getBlockY(),
					wand.owner.getLocation().getBlockZ(),
					true);
		}
		
		// Shift the origin of the clipboard
		else if (args[1].equalsIgnoreCase("origin")) {
			if (args[2].equalsIgnoreCase("set")) {
				if (args[3].equalsIgnoreCase("x")) {
					ClipboardManager.getClipboard(wand.owner).x = Integer.parseInt(args[4]);
					wand.owner.sendMessage("§dSelection origin set");
					return true;
				}
				else if (args[3].equalsIgnoreCase("y")) {
					ClipboardManager.getClipboard(wand.owner).y = Integer.parseInt(args[4]);
					wand.owner.sendMessage("§dSelection origin set");
					return true;
				}
				else if (args[3].equalsIgnoreCase("z")) {
					ClipboardManager.getClipboard(wand.owner).z = Integer.parseInt(args[4]);
					wand.owner.sendMessage("§dSelection origin set");
					return true;
				}
			}
			else if (args[2].equalsIgnoreCase("shift")) {
				if (args[3].equalsIgnoreCase("x")) {
					ClipboardManager.getClipboard(wand.owner).x = ClipboardManager.getClipboard(wand.owner).x + Integer.parseInt(args[4]);
					wand.owner.sendMessage("§dSelection origin shifted");
					return true;
				}
				else if (args[3].equalsIgnoreCase("y")) {
					ClipboardManager.getClipboard(wand.owner).y = ClipboardManager.getClipboard(wand.owner).z + Integer.parseInt(args[4]);
					wand.owner.sendMessage("§dSelection origin shifted");
					return true;
				}
				else if (args[3].equalsIgnoreCase("z")) {
					ClipboardManager.getClipboard(wand.owner).z = ClipboardManager.getClipboard(wand.owner).y + Integer.parseInt(args[4]);
					wand.owner.sendMessage("§dSelection origin shifted");
					return true;
				}
			}
			return false;
		}
		
		// Reset a selection
		else if (args[1].equalsIgnoreCase("reset")) {
			player.sendMessage("§dRegion reset");
			return manager.resetSelection();
		}
		
		// Handles schematics
		else if (args[1].equalsIgnoreCase("schematic") || args[1].equalsIgnoreCase("schem")) {
			if (args[2].equalsIgnoreCase("save")) {
				wand.owner.sendMessage("§dSaving schematic");
				return ClipboardManager.getClipboard(wand.owner).saveToFile(args[3]);
			}
			else if (args[2].equalsIgnoreCase("load")) {
				wand.owner.sendMessage("§dLoading schematic");
				return ClipboardManager.getClipboard(wand.owner).loadFromFile(args[3]);
			}
			return false;
		}
		
		else {
			return false;
		}
	}
	
	// Operate on the selection
	private static boolean operate (SelectionManager manager, SelectionWand wand, String[] brushOperation) {
		// Build an array of blocks within this selection
		List<Block> blockArray = manager.getBlocks();
		if (Main.isDebug) Bukkit.getServer().broadcastMessage("§c[DEBUG] Block array size is " + Integer.toString(blockArray.size())); // -----
		
		// Store an undo
		UndoManager.getUndo(wand.owner).storeUndo(UndoElement.newUndoElement(blockArray));
		
		// Construct the operation
		int brushOpOffset = 2;
		List<String> opArray = new LinkedList<String>(Arrays.asList(brushOperation));
		while (brushOpOffset > 0) {
			opArray.remove(0);
			brushOpOffset--;
		}
		// Construct the string
		String opStr = "";
		for (String s : opArray) {
			opStr = opStr.concat(s).concat(" ");
		}
		// And turn the string into an operation
		Operator operator = Operator.newOperator(opStr);
		
		// Finally, perform the operation
		if (operator == null)
			return false;
		if (Main.isDebug) Bukkit.getServer().broadcastMessage("§c[DEBUG] Operating on selection"); // -----
		for (Block b : blockArray) {
			operator.operateOnBlock(b, wand.owner);
		}
		return true;
	}
	
	// Expand the selection
	// Note that using the wand afterwards doesn't reflect the changes
	private static boolean expand (SelectionManager manager, double amt, String dir, Player player) {
		return manager.expandSelection(amt, dir, player);
	}
}
