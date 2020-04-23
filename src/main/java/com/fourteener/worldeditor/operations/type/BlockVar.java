package com.fourteener.worldeditor.operations.type;

public class BlockVar {
	String type = "";
	byte data = 0;
	String nbt = "";
	
	public String getType() {
		return type;
	}
	public byte getData() {
		return data;
	}
	public String getNBT() {
		return nbt;
	}
	
	public void setType(String newVal) {
		type = newVal;
	}
	public void setData(String newVal) {
		data = Byte.parseByte(newVal);
	}
	public void setNbt(String newVal) {
		nbt = newVal;
	}
}
