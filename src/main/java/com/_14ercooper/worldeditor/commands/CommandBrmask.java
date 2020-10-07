package com._14ercooper.worldeditor.commands;

import java.util.HashSet;

import com._14ercooper.worldeditor.main.GlobalVars;
import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Material;
import com._14ercooper.worldeditor.wrapper.Player;

public class CommandBrmask implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	try {
	    GlobalVars.brushMask = new HashSet<Material>();
	    for (String s : args) {
		GlobalVars.brushMask.add(Material.matchMaterial(s));
	    }

	    return true;
	}
	catch (Exception e) {
	    Main.logError("Failed to set block mask.", sender);
	    return false;
	}
    }
}
