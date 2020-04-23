package com.fourteener.worldeditor.main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class SetBlock {
	public static void setMaterial(Block b, Material mat) {
		if (GlobalVars.currentUndo != null)
			GlobalVars.currentUndo.storeBlock(b);
		b.setType(mat, false);
	}
	public static void setMaterial(Block b, Material mat, boolean physics) {
		if (GlobalVars.currentUndo != null)
			GlobalVars.currentUndo.storeBlock(b);
		b.setType(mat, physics);
	}
	public static void setMaterial(BlockState b, Material mat) {
		if (GlobalVars.currentUndo != null)
			GlobalVars.currentUndo.storeBlock(b.getBlock());
		b.setType(mat);
	}
}
