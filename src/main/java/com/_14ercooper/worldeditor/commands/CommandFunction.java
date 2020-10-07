package com._14ercooper.worldeditor.commands;

import java.util.ArrayList;
import java.util.List;

import com._14ercooper.worldeditor.functions.Function;
import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

public class CommandFunction implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender arg0, String arg2, String[] arg3) {
	if (arg0 instanceof Player) {
	    if (!((Player) arg0).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", arg0);
		return false;
	    }
	}

	if (!(arg0 instanceof Player)) {
	    Main.logError("This command must be run as a player.", arg0);
	    return false;
	}

	Player player = (Player) arg0;
	String filename = "";
	try {
	    filename = arg3[0];
	}
	catch (Exception e) {
	    Main.logError("Must provide at least a filename.", player);
	    return false;
	}
	List<String> args = new ArrayList<String>();
	for (int i = 1; i < arg3.length; i++) {
	    args.add(arg3[i]);
	}
	Function fx = new Function(filename, args, player, false);
	fx.run();
	return true;
    }
}
