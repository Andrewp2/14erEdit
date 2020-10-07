package com._14ercooper.worldeditor.main;

import com._14ercooper.worldeditor.wrapper.Block;

public class NBTExtractor {
    public String getNBT(Block b) {
	return b.getNBT();
    }
    
    public void setNBT(Block b, String nbt) {
	b.setNBT(nbt);
    }
}
