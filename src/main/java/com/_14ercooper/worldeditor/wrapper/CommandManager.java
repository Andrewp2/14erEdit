package com._14ercooper.worldeditor.wrapper;

public class CommandManager {
    public static void addCommand(String commandLabel, Object executor) {
	if (!(executor instanceof CommandExecutor)) {
	    return;
	}
	CommandExecutor commandExecutor = (CommandExecutor) executor;
	
	// TODO
    }
}
