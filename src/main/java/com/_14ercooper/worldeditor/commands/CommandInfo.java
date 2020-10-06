package com._14ercooper.worldeditor.commands;

import com._14ercooper.worldeditor.main.Main;

public class CommandInfo implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		sender.sendMessage("You must be opped to use 14erEdit");
		return false;
	    }
	}

	try {
	    Bukkit.broadcastMessage("§d14erEdit is running properly");

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
