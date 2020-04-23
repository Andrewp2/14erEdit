package com.fourteener.worldeditor.operations.operators.world;

import org.bukkit.Material;

import com.fourteener.worldeditor.main.*;
import com.fourteener.worldeditor.operations.Operator;
import com.fourteener.worldeditor.operations.operators.Node;

public class BlockNode extends Node {
	
	// Stores this node's argument
	public Material arg1;
	public byte arg2 = -1;
	
	// Creates a new node
	public BlockNode newNode() {
		BlockNode node = new BlockNode();
		node.arg1 = Material.matchMaterial(GlobalVars.operationParser.parseStringNode().contents);
		return node;
	}
	
	// Creates a new node
	public BlockNode newNode(boolean overload) {
		BlockNode node = new BlockNode();
		String data = GlobalVars.operationParser.parseStringNode().contents;
		node.arg1 = Material.matchMaterial(data.split(":")[0]);
		node.arg2 = Byte.parseByte(data.split(":")[1]);
		return node;
	}
	
	// Return the material this node references
	public Material getBlock () {
		return arg1;
	}
	
	// Get the data of this block
	public byte getData () {
		return arg2;
	}
	
	// Check if it's the correct block
	@SuppressWarnings("deprecation")
	public boolean performNode () {
		if (arg2 == -1) {
			return Operator.currentBlock.getType().equals(arg1);
		}
		else {
			return Operator.currentBlock.getData() == arg2 && Operator.currentBlock.getType().equals(arg1);
		}
	}
	
	// Returns how many arguments this node takes
	public int getArgCount () {
		return 1;
	}
}
