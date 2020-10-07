package com._14ercooper.worldeditor.commands;

import com._14ercooper.worldeditor.main.GlobalVars;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

// These are dedicated versions of the undo and redo commands
public class CommandDebug implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	GlobalVars.isDebug = !GlobalVars.isDebug;
	Broadcaster.broadcastAll("Debug toggled to " + GlobalVars.isDebug);
	return true;
    }
}
