package com._14ercooper.worldeditor.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CommandExecutor;
import com._14ercooper.worldeditor.wrapper.CommandSender;
import com._14ercooper.worldeditor.wrapper.Player;

public class CommandTemplate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender arg0, String arg2, String[] arg3) {
	if (arg0 instanceof Player) {
	    if (!((Player) arg0).isOp()) {
		Broadcaster.broadcastSingle("You must be opped to use 14erEdit.", arg0);
		return false;
	    }
	}

	CommandSender player = arg0;

	// Grab the filename
	String filename;
	try {
	    filename = "plugins/14erEdit/templates/" + arg3[0];
	}
	catch (Exception e) {
	    Main.logError("Template name required to use this command.", player);
	    return false;
	}

	// Extension expansion
	if (Files.exists(Paths.get(filename))) {
	    // Filename is good, keep it
	}
	else if (Files.exists(Paths.get(filename + ".txt"))) {
	    filename += ".txt";
	}
	else if (Files.exists(Paths.get(filename + ".fx"))) {
	    filename += ".fx";
	}
	else {
	    Main.logError("Template not found.", player);
	    return false;
	}

	// Grab the command
	String command;
	try {
	    command = readFile(filename);
	}
	catch (IOException e) {
	    Main.logError("Error reading template file.", player);
	    return false;
	}

	// Clean up newlines
	command = command.replaceAll("[\\n\\r]+", " ");

	// Fill in template
	for (int i = arg3.length - 1; i >= 1; i--) {
	    command = command.replaceAll("\\$" + i, arg3[i]);
	}

	Main.logDebug("Template command: " + command);

	// Run the command
	try {
	    return player.dispatchCommand(command);
	}
	catch (Exception e) {
	    Main.logError("Could not run command in template.", player);
	    return false;
	}
    }

    static String readFile(String path) throws IOException {
	byte[] encoded = Files.readAllBytes(Paths.get(path));
	return new String(encoded, StandardCharsets.UTF_8);
    }

}
