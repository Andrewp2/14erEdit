package com._14ercooper.worldeditor.commands;

import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.operations.Operator;
import com._14ercooper.worldeditor.wrapper.Block;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

// These are dedicated versions of the undo and redo commands
public class CommandRun implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	try {
	    if (sender instanceof Player) {
		String opStr = "";
		for (String s : args) {
		    opStr = opStr.concat(s).concat(" ");
		}
		Operator op = new Operator(opStr, (Player) sender);
		Block b = ((Player) sender).getWorld().getBlockAt(((Player) sender).getLocation());
		op.operateOnBlock(b, (Player) sender);
		return true;
	    }
	    Main.logError("This must be run as a player.", sender);
	    return false;
	}
	catch (Exception e) {
	    Main.logError("Error performing run command. Please check your syntax.", sender);
	    return false;
	}
    }
}
