package com._14ercooper.worldeditor.operations.operators.query;

import org.bukkit.block.Block;

import com._14ercooper.worldeditor.main.GlobalVars;
import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.main.NBTExtractor;
import com._14ercooper.worldeditor.operations.Operator;
import com._14ercooper.worldeditor.operations.operators.Node;
import com._14ercooper.worldeditor.operations.operators.core.StringNode;
import com._14ercooper.worldeditor.operations.operators.world.BlockNode;

public class BlockAtNode extends BlockNode {

	StringNode x, y, z;
	int xV, yV, zV;
	boolean xA = false, yA = false, zA = false;
	Node node;

	public BlockAtNode newNode() {
		BlockAtNode baNode = new BlockAtNode();
		try {
			baNode.x = GlobalVars.operationParser.parseStringNode();
			baNode.y = GlobalVars.operationParser.parseStringNode();
			baNode.z = GlobalVars.operationParser.parseStringNode();
			if (x.contents.contains("a")) xA = true;
			if (y.contents.contains("a")) yA = true;
			if (z.contents.contains("a")) zA = true;
			xV = Integer.parseInt(x.contents.replaceAll("[^0-9]", ""));
			yV = Integer.parseInt(y.contents.replaceAll("[^0-9]", ""));
			zV = Integer.parseInt(z.contents.replaceAll("[^0-9]", ""));
			try {
				baNode.node = GlobalVars.operationParser.parsePart();
			} catch (Exception e) {
				Main.logDebug("Block at created with type blocknode");
			}
		} catch (Exception e) {
			Main.logError("Error creating block at node. Please check your syntax.", Operator.currentPlayer);
			return null;
		}
		if (baNode.z == null) {
			Main.logError("Could not parse block at node. Three numbers and optionally an operation are required, but not given.", Operator.currentPlayer);
		}
		return baNode;
	}

	public boolean performNode() {
		try {
			Block currBlock = Operator.currentBlock;
			Operator.currentBlock = Operator.currentPlayer.getWorld().getBlockAt(
					xV + (xA ? 0 : currBlock.getX()),
					yV + (yA ? 0 : currBlock.getY()),
					zV + (zA ? 0 : currBlock.getZ()));
			boolean matches = node.performNode();
			Operator.currentBlock = currBlock;
			return matches;
		} catch (Exception e) {
			Main.logError("Error performing block at node. Please check your syntax.", Operator.currentPlayer);
			return false;
		}
	}
	
	// Return the material this node references
	public String getBlock () {
		return Operator.currentPlayer.getWorld().getBlockAt(
				xV + (xA ? 0 : Operator.currentBlock.getX()),
				yV + (yA ? 0 : Operator.currentBlock.getY()),
				zV + (zA ? 0 : Operator.currentBlock.getZ())).toString();
	}

	// Get the data of this block
	public String getData () {
		return Operator.currentPlayer.getWorld().getBlockAt(
				xV + (xA ? 0 : Operator.currentBlock.getX()),
				yV + (yA ? 0 : Operator.currentBlock.getY()),
				zV + (zA ? 0 : Operator.currentBlock.getZ())).toString();
	}
	
	// Get the NBT of this block
	public String getNBT() {
		NBTExtractor nbt = new NBTExtractor();
		return nbt.getNBT(Operator.currentPlayer.getWorld().getBlockAt(
				xV + (xA ? 0 : Operator.currentBlock.getX()),
				yV + (yA ? 0 : Operator.currentBlock.getY()),
				zV + (zA ? 0 : Operator.currentBlock.getZ())));
	}

	public int getArgCount() {
		return 4;
	}

}
