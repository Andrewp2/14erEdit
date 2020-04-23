package com.fourteener.worldeditor.operations.operators.world;

import com.fourteener.worldeditor.main.*;
import com.fourteener.worldeditor.operations.Operator;
import com.fourteener.worldeditor.operations.operators.Node;

public class SetPlusNode extends Node {

	public BlockNode arg;
	
	public SetPlusNode newNode() {
		SetPlusNode node = new SetPlusNode();
		node.arg = (BlockNode) GlobalVars.operationParser.parsePart();
		return node;
	}
	
	@SuppressWarnings("deprecation")
	public boolean performNode () {
		boolean didSet = true;
		if (Operator.currentBlock.getType() == arg.getBlock()) {
			didSet = false;
		}
		if (Operator.currentBlock.getData() == arg.getData()) {
			didSet = false;
		}
		SetBlock.setMaterial(Operator.currentBlock, arg.getBlock());
		Operator.currentBlock.setData(arg.getData());
		return didSet;
	}
	
	public int getArgCount () {
		return 1;
	}
}
