package com._14ercooper.worldeditor.functions.commands.logic;

import java.util.List;

import com._14ercooper.worldeditor.functions.Function;
import com._14ercooper.worldeditor.functions.commands.InterpreterCommand;
import com._14ercooper.worldeditor.wrapper.Material;

public class CompareSlotCommand extends InterpreterCommand {

    @Override
    public void run(List<String> args, Function function) {
	int slot = 0;
	if (args.get(0).equalsIgnoreCase("hand")) {
	    slot = -1;
	}
	else {
	    slot = Integer.parseInt(args.get(0));
	}

	Material handItem = slot >= 0 ? function.player.getSlot(slot)
		: function.player.getMainhand();
	String[] elements = args.get(1).split(";");
	boolean foundMatch = false;
	for (String s : elements) {
	    if (s.contains("#")) {
		foundMatch = foundMatch || handItem.toString().toLowerCase().contains(s.substring(1).toLowerCase());
	    }
	    else {
		foundMatch = foundMatch || handItem == Material.matchMaterial(s);
	    }
	}
	if (foundMatch)
	    function.cmpres = 1;
	else
	    function.cmpres = 0;
    }
}
