package com._14ercooper.worldeditor.commands;

import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

public class CommandInfo implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	try {
	    Broadcaster.broadcastAll("14erEdit is running properly.");

	    return true;
	}
	catch (Exception e) {
	    Main.logError(
		    "It's obviously doing something wrong. If you ever see this, tell 14er. This should be unreachable code.",
		    sender);
	    return false;
	}
    }
}
