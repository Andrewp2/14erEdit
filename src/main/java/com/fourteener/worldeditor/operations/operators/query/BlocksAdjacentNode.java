package com.fourteener.worldeditor.operations.operators.query;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.fourteener.worldeditor.main.*;
import com.fourteener.worldeditor.operations.Operator;
import com.fourteener.worldeditor.operations.operators.Node;
import com.fourteener.worldeditor.operations.operators.function.RangeNode;

public class BlocksAdjacentNode extends Node {
	
	public Node arg1;
	public RangeNode arg2;
	
	public BlocksAdjacentNode newNode() {
		BlocksAdjacentNode node = new BlocksAdjacentNode();
		node.arg2 = GlobalVars.operationParser.parseRangeNode();
		node.arg1 = GlobalVars.operationParser.parsePart();
		return node;
	}
	
	public boolean performNode () {
		Main.logDebug("Performing faces exposed node"); // -----
		
		// Check if any adjacent blocks match arg1
		// Set up some variables
		int numAdjacentBlocks = 0;
		Block curBlock = GlobalVars.world.getBlockAt(Operator.currentBlock.getLocation());
		
		// Check each direction
		Block blockAdj = curBlock.getRelative(BlockFace.NORTH);
		Operator.currentBlock = blockAdj;
		if (arg1.performNode())
			numAdjacentBlocks++;
		blockAdj = curBlock.getRelative(BlockFace.SOUTH);
		Operator.currentBlock = blockAdj;
		if (arg1.performNode())
			numAdjacentBlocks++;
		blockAdj = curBlock.getRelative(BlockFace.EAST);
		Operator.currentBlock = blockAdj;
		if (arg1.performNode())
			numAdjacentBlocks++;
		blockAdj = curBlock.getRelative(BlockFace.WEST);
		Operator.currentBlock = blockAdj;
		if (arg1.performNode())
			numAdjacentBlocks++;
		blockAdj = curBlock.getRelative(BlockFace.UP);
		Operator.currentBlock = blockAdj;
		if (arg1.performNode())
			numAdjacentBlocks++;
		blockAdj = curBlock.getRelative(BlockFace.DOWN);
		Operator.currentBlock = blockAdj;
		if (arg1.performNode())
			numAdjacentBlocks++;
		
		// Reset the current block
		Operator.currentBlock = curBlock;
		
		return (numAdjacentBlocks >= arg2.getMin() - 0.1 && numAdjacentBlocks <= arg2.getMax() + 0.1);
	}
	
	public int getArgCount () {
		return 2;
	}
}