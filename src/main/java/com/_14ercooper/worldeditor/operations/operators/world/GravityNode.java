package com._14ercooper.worldeditor.operations.operators.world;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com._14ercooper.worldeditor.main.GlobalVars;
import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.operations.Operator;
import com._14ercooper.worldeditor.operations.operators.Node;

public class GravityNode extends Node {

    @Override
    public GravityNode newNode() {
	return new GravityNode();
    }

    @Override
    public boolean performNode() {
	try {
	    Material mat = Operator.currentBlock.getType();
	    Block b = Operator.currentBlock;
	    while (true) {
		if (b.getX() == 0 || !GlobalVars.brushMask.contains(b.getRelative(BlockFace.DOWN).getType()))
		    break;
		b = b.getRelative(BlockFace.DOWN);
	    }
	    b.setType(mat);
	    Operator.currentBlock.setType(Material.AIR);
	    return true;
	}
	catch (Exception e) {
	    Main.logError("Error performing gravity node. Please check your syntax (or tell 14er how you got here).",
		    Operator.currentPlayer);
	    return false;
	}
    }

    @Override
    public int getArgCount() {
	return 0;
    }

}
