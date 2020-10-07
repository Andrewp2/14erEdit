package com._14ercooper.worldeditor.commands;

import java.util.Arrays;
import java.util.LinkedList;

import com._14ercooper.worldeditor.main.GlobalVars;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

public class CommandScript implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	if (sender instanceof Player) {
	    LinkedList<String> argsToPass = new LinkedList<String>(Arrays.asList(args));
	    argsToPass.removeFirst();
	    return GlobalVars.scriptManager.runCraftscript(args[0], argsToPass, (Player) sender);
	}
	return false;
    }
}
