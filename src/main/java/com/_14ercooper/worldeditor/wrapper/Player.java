package com._14ercooper.worldeditor.wrapper;

public class Player implements BroadcastTarget, CommandSender {

    @Override
    public void sendMessage() {
	// TODO Auto-generated method stub

    }

    public String getName() {
	// TODO
	return "";
    }
    
    public boolean isOp() {
	// TODO
	return false;
    }

    @Override
    public boolean dispatchCommand(String command) {
	// TODO Auto-generated method stub
	return false;
    }

    public World getWorld() {
	// TODO Auto-generated method stub
	return null;
    }

    public Location getLocation() {
	// TODO Auto-generated method stub
	return null;
    }

    public Player getPlayer() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void sendMessage(String string) {
	// TODO Auto-generated method stub
	
    }

    public Material getSlot(int id) {
	// TODO Auto-generated method stub
	return null;
    }
    
    public Material getMainhand() {
	// TODO
	return null;
    }
    
    public Material getOffhand() {
	// TODO
	return null;
    }

    public void setSlot(int slot, Material toSet) {
	// TODO Auto-generated method stub
	
    }

    public void setHand(Material toSet) {
	// TODO Auto-generated method stub
	
    }
}
