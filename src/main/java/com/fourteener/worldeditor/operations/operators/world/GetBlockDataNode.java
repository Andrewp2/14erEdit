package com.fourteener.worldeditor.operations.operators.world;

import com.fourteener.worldeditor.operations.Operator;
import com.fourteener.worldeditor.operations.operators.Node;

public class GetBlockDataNode extends Node {
	
	public GetBlockDataNode newNode() {
		return new GetBlockDataNode();
	}
	
	public boolean performNode () {
		@SuppressWarnings("deprecation")
		byte s = Operator.currentBlock.getData();
		Operator.currentPlayer.sendMessage("§dBlock Data: " + s);
		return s > 0;
	}
	
	public int getArgCount () {
		return 0;
	}
}
