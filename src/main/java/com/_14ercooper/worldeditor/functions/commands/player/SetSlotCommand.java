package com._14ercooper.worldeditor.functions.commands.player;

import java.util.List;

import com._14ercooper.worldeditor.functions.Function;
import com._14ercooper.worldeditor.functions.commands.InterpreterCommand;
import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.wrapper.Material;

public class SetSlotCommand extends InterpreterCommand {

    @Override
    public void run(List<String> args, Function function) {
	int slot = 0;
	if (args.get(0).equalsIgnoreCase("hand")) {
	    slot = -1;
	}
	else {
	    slot = (int) function.parseVariable(args.get(0));
	}

	Material toSet = Material.matchMaterial(args.get(1));
	if (toSet == null) {
	    Main.logError("Item ID not known: " + args.get(1), function.player);
	}
	else {
	    if (slot != -1) {
		function.player.setSlot(slot, toSet);
	    }
	    else {
		function.player.setHand(toSet);
	    }
	}
    }
}
