package com._14ercooper.worldeditor.functions.commands.player;

import java.util.List;

import com._14ercooper.worldeditor.functions.Function;
import com._14ercooper.worldeditor.functions.commands.InterpreterCommand;
import com._14ercooper.worldeditor.wrapper.Material;

public class SwapCommand extends InterpreterCommand {

    @Override
    public void run(List<String> args, Function function) {
	int slot1 = (int) function.parseVariable(args.get(0));
	int slot2 = (int) function.parseVariable(args.get(1));
	Material first = function.player.getSlot(slot1);
	Material second = function.player.getSlot(slot2);
	function.player.setSlot(slot2, first);
	function.player.setSlot(slot1, second);
    }
}
