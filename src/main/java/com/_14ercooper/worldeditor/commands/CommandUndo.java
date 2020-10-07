package com._14ercooper.worldeditor.commands;

import com._14ercooper.worldeditor.undo.UndoManager;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

// These are dedicated versions of the undo and redo commands
public class CommandUndo implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
	if (sender instanceof Player) {
	    if (!((Player) sender).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", sender);
		return false;
	    }
	}

	if (sender instanceof Player) {
	    if (label.equalsIgnoreCase("un")) {
		int numToUndo = 1;
		try {
		    numToUndo = Integer.parseInt(args[0]);
		}
		catch (Exception e) {
		    numToUndo = 1;
		}
		return UndoManager.getUndo((Player) sender).undoChanges(numToUndo) > 0;
	    }
	    else if (label.equalsIgnoreCase("re")) {
		int numToRedo = 1;
		try {
		    numToRedo = Integer.parseInt(args[0]);
		}
		catch (Exception e) {
		    numToRedo = 1;
		}
		return UndoManager.getUndo((Player) sender).redoChanges(numToRedo) > 0;
	    }
	    return false;
	}
	return false;
    }
}
