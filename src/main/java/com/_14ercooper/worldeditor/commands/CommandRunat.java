package com._14ercooper.worldeditor.commands;

import com._14ercooper.worldeditor.brush.BrushShape;
import com._14ercooper.worldeditor.brush.shapes.Voxel;
import com._14ercooper.worldeditor.main.GlobalVars;
import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.operations.Operator;
import com._14ercooper.worldeditor.wrapper.BlockCommandSender;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Entity;
import com._14ercooper.worldeditor.wrapper.Player;

public class CommandRunat implements CommandExecutor {

    @SuppressWarnings("static-access")
    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	try {
	    double x = 0, y = 0, z = 0;
	    // X with relative
	    if (args[0].contains("~")) {
		if (sender instanceof BlockCommandSender) {
		    x = Double.parseDouble(args[0].replaceAll("~", ""))
			    + ((BlockCommandSender) sender).getBlock().getX();
		}
		if (sender instanceof Entity) {
		    x = Double.parseDouble(args[0].replaceAll("~", "")) + ((Entity) sender).getLocation().getX();
		}
	    }
	    else {
		x = Double.parseDouble(args[0]);
	    }
	    // Y with relative
	    if (args[1].contains("~")) {
		if (sender instanceof BlockCommandSender) {
		    y = Double.parseDouble(args[1].replaceAll("~", ""))
			    + ((BlockCommandSender) sender).getBlock().getY();
		}
		if (sender instanceof Entity) {
		    y = Double.parseDouble(args[1].replaceAll("~", "")) + ((Entity) sender).getLocation().getY();
		}
	    }
	    else {
		y = Double.parseDouble(args[1]);
	    }
	    // Z with relative
	    if (args[0].contains("~")) {
		if (sender instanceof BlockCommandSender) {
		    z = Double.parseDouble(args[2].replaceAll("~", ""))
			    + ((BlockCommandSender) sender).getBlock().getZ();
		}
		if (sender instanceof Entity) {
		    z = Double.parseDouble(args[2].replaceAll("~", "")) + ((Entity) sender).getLocation().getZ();
		}
	    }
	    else {
		z = Double.parseDouble(args[2]);
	    }

	    try {
		BrushShape shape = new Voxel();
		String opStr = "";
		for (int i = 3; i < args.length; i++) {
		    opStr += args[i] + " ";
		}
		Operator op = new Operator(opStr, (Player) sender);
		GlobalVars.asyncManager.scheduleEdit(op, null, shape.GetBlocks(x, y, z));
		return true;
	    }
	    catch (Exception e) {
		Main.logError("Error in runat. Please check your syntax.", sender);
		return false;
	    }
	}
	catch (Exception e) {
	    Main.logError("Could not parse runat command. Please check your syntax.", sender);
	    return false;
	}
    }
}
