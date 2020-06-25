package com._14ercooper.worldeditor.async;

import java.util.ArrayDeque;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com._14ercooper.schematics.SchemLite;
import com._14ercooper.worldeditor.blockiterator.BlockIterator;
import com._14ercooper.worldeditor.operations.Operator;
import com._14ercooper.worldeditor.undo.Undo;

public class AsyncOperation {
	protected String key = "";
	protected Operator operation = null;
	protected ArrayDeque<Block> toOperate = null;
	protected BlockIterator blocks = null;
	protected Player player = null;
	protected Undo undo = null;
	protected boolean undoRunning = false;
	
	public AsyncOperation (Operator o, Player p, BlockIterator b) {
		key = "iteredit";
		operation = o;
		player = p;
		blocks = b;
	}
	
	public AsyncOperation (Operator o, BlockIterator b) {
		key = "rawiteredit";
		operation = o;
		blocks = b;
	}
	
	public AsyncOperation (Operator o, Player p, ArrayDeque<Block> b) {
		key = "edit";
		operation = o;
		player = p;
		toOperate = b;
	}
	
	public AsyncOperation (Operator o, ArrayDeque<Block> b) {
		key = "rawedit";
		operation = o;
		toOperate = b;
	}
	
	public AsyncOperation (Operator o) {
		key = "messyedit";
		operation = o;
	}
	
	// New schematics system
	protected SchemLite schem = null;
	protected int[] origin = {};
	
	public AsyncOperation (SchemLite sl, boolean saveSchem, int[] o, Player p) {
		schem = sl;
		origin = o;
		blocks = schem.getIterator(origin[0], origin[1], origin[2]);
		if (saveSchem) {
			key = "saveschem";
		}
		else {
			key = "loadschem";
		}
		player = p;
	}
	
	// Selection move/stack
	protected int[] offset = {};
	protected int times = 0;
	protected boolean delOriginal = false;
	// Uses the same iterator as other functions
	
	public AsyncOperation (BlockIterator selectionIter, int[] cloneOffset, int cloneTimes, boolean delOriginalBlocks, Player p) {
		key = "selclone";
		blocks = selectionIter;
		offset = cloneOffset;
		times = cloneTimes;
		delOriginal = delOriginalBlocks;
		player = p;
	}
}