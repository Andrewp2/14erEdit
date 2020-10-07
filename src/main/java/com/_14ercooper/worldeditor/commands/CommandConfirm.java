package com._14ercooper.worldeditor.commands;

import com._14ercooper.worldeditor.main.GlobalVars;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

// These are dedicated versions of the undo and redo commands
public class CommandConfirm implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	if (sender instanceof Player) {
	    if (label.equalsIgnoreCase("confirm")) {
		if (args.length > 0 && args[0].equalsIgnoreCase("auto")) {
		    GlobalVars.autoConfirm = !GlobalVars.autoConfirm;
		    return true;
		}
		int numToConfirm = 1;
		try {
		    numToConfirm = Integer.parseInt(args[0]);
		}
		catch (Exception e) {
		    numToConfirm = 1;
		}
		GlobalVars.asyncManager.confirmEdits(numToConfirm);
		return true;
	    }
	    else if (label.equalsIgnoreCase("cancel")) {
		int numToCancel = 1;
		try {
		    numToCancel = Integer.parseInt(args[0]);
		}
		catch (Exception e) {
		    numToCancel = 1;
		}
		GlobalVars.asyncManager.cancelEdits(numToCancel);
		return true;
	    }
	    return false;
	}
	return false;
    }
}
