package com._14ercooper.worldeditor.main;

import com._14ercooper.worldeditor.operations.Operator;
import com._14ercooper.worldeditor.wrapper.Block;
import com._14ercooper.worldeditor.wrapper.BlockState;
import com._14ercooper.worldeditor.wrapper.Material;

public class SetBlock {
    public static void setMaterial(Block b, Material mat) {
	try {
	    if (GlobalVars.currentUndo != null)
		GlobalVars.currentUndo.storeBlock(b);
	    b.setType(mat, false);
	    if (b.dataContains("persistent")) {
		b.setData("persistent", "true");
	    }
	}
	catch (Exception e) {
	    Main.logError("Invalid block ID provided. The async queue has been dropped.", Operator.currentPlayer);
	    GlobalVars.asyncManager.dropAsync();
	    return;
	}
    }

    public static void setMaterial(Block b, Material mat, boolean physics) {
	try {
	    if (GlobalVars.currentUndo != null)
		GlobalVars.currentUndo.storeBlock(b);
	    b.setType(mat, physics);
	    if (b.dataContains("persistent")) {
		b.setData("persistent", "true");
	    }
	}
	catch (Exception e) {
	    Main.logError("Invalid block ID provided. The async queue has been dropped.", Operator.currentPlayer);
	    GlobalVars.asyncManager.dropAsync();
	    return;
	}
    }

    public static void setMaterial(BlockState b, Material mat) {
	try {
	    if (GlobalVars.currentUndo != null)
		GlobalVars.currentUndo.storeBlock(b.getBlock());
	    b.setType(mat);
	    if (b.dataContains("persistent")) {
		b.setData("persistent", "true");
	    }
	}
	catch (Exception e) {
	    Main.logError("Invalid block ID provided. The async queue has been dropped.", Operator.currentPlayer);
	    GlobalVars.asyncManager.dropAsync();
	    return;
	}
    }
}
