package com._14ercooper.worldeditor.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

import com._14ercooper.worldeditor.async.AsyncManager;
import com._14ercooper.worldeditor.blockiterator.IteratorLoader;
import com._14ercooper.worldeditor.blockiterator.IteratorManager;
import com._14ercooper.worldeditor.brush.BrushListener;
import com._14ercooper.worldeditor.brush.BrushLoader;
import com._14ercooper.worldeditor.commands.*;
import com._14ercooper.worldeditor.functions.Function;
import com._14ercooper.worldeditor.macros.MacroLauncher;
import com._14ercooper.worldeditor.macros.MacroLoader;
import com._14ercooper.worldeditor.make.MakeSetup;
import com._14ercooper.worldeditor.operations.OperatorLoader;
import com._14ercooper.worldeditor.operations.Parser;
import com._14ercooper.worldeditor.scripts.CraftscriptLoader;
import com._14ercooper.worldeditor.scripts.CraftscriptManager;
import com._14ercooper.worldeditor.selection.SelectionWandListener;
import com._14ercooper.worldeditor.wrapper.BroadcastTarget;
import com._14ercooper.worldeditor.wrapper.Broadcaster;
import com._14ercooper.worldeditor.wrapper.CallbackManager;
import com._14ercooper.worldeditor.wrapper.CommandManager;
import com._14ercooper.worldeditor.wrapper.Initializer;
import com._14ercooper.worldeditor.wrapper.Material;
import com._14ercooper.worldeditor.wrapper.World;

public class Main extends Initializer {

    @Override
    public void onEnable() {
	// Create folders as needed
	try {
	    Files.createDirectories(Paths.get(GlobalVars.rootDir + "/schematics"));
	    Files.createDirectories(Paths.get(GlobalVars.rootDir + "/ops"));
	    Files.createDirectories(Paths.get(GlobalVars.rootDir + "/Commands"));
	    Files.createDirectories(Paths.get(GlobalVars.rootDir + "/vars"));
	    Files.createDirectories(Paths.get(GlobalVars.rootDir + "/templates"));
	    Files.createDirectories(Paths.get(GlobalVars.rootDir + "/multibrushes"));
	    Files.createDirectories(Paths.get(GlobalVars.rootDir + "/functions"));
	}
	catch (IOException e) {
	    Main.logDebug("Error creating directory structure. 14erEdit may not work properly until this is resolved.");
	}
	
	// Load values from config into global vars
	// TODO

	// Register commands with the server
	CommandManager.addCommand("fx", new CommandFx());
	CommandUndo undoCmd = new CommandUndo();
	CommandManager.addCommand("un", undoCmd);
	CommandManager.addCommand("re", undoCmd);
	CommandConfirm confirmCmd = new CommandConfirm();
	CommandManager.addCommand("confirm", confirmCmd);
	CommandManager.addCommand("cancel", confirmCmd);
	CommandManager.addCommand("script", new CommandScript());
	CommandManager.addCommand("run", new CommandRun());
	CommandManager.addCommand("runat", new CommandRunat());
	CommandManager.addCommand("debug", new CommandDebug());
	CommandManager.addCommand("14erEdit", new CommandInfo());
	CommandManager.addCommand("async", new CommandAsync());
	CommandManager.addCommand("brmask", new CommandBrmask());
	CommandManager.addCommand("template", new CommandTemplate());
	CommandManager.addCommand("funct", new CommandFunction());
	CommandManager.addCommand("make", new CommandMake());

	// Set up brush mask
	GlobalVars.brushMask = new HashSet<Material>();
	GlobalVars.brushMask.add(Material.matchMaterial("minecraft:air"));
	GlobalVars.brushMask.add(Material.matchMaterial("minecraft:cave_air"));
	GlobalVars.brushMask.add(Material.matchMaterial("minecraft:void_air"));

	// Register listeners for brushes and wands
	CallbackManager.registerNowCallback(new SelectionWandListener());
	CallbackManager.registerNowCallback(new BrushListener());

	// These are needed by the plugin, but should only be loaded once as they are
	// very slow to load
	GlobalVars.noiseSeed = (int) World.getSeed(); // Seeded using the world seed
									  // for variance between worlds
									  // but consistency in the same
									  // world
	GlobalVars.simplexNoise = new SimplexNoise(World.getSeed());

	// Load managers
	GlobalVars.scriptManager = new CraftscriptManager();
	GlobalVars.macroLauncher = new MacroLauncher();
	GlobalVars.operationParser = new Parser();
	GlobalVars.asyncManager = new AsyncManager();
	GlobalVars.iteratorManager = new IteratorManager();

	// Register the prepackaged things to managers
	CraftscriptLoader.LoadBundledCraftscripts();
	MacroLoader.LoadMacros();
	BrushLoader.LoadBrushes();
	OperatorLoader.LoadOperators();
	IteratorLoader.LoadIterators();
	Function.SetupFunctions();
	MakeSetup.registerMakes();
    }

    public static void logDebug(String message) {
	if (GlobalVars.isDebug)
	    Broadcaster.broadcastAll("§c[DEBUG] " + message); // ----
	try {
	    if (GlobalVars.logDebugs) {
		if (!Files.exists(Paths.get(GlobalVars.rootDir + "/debug.log")))
		    Files.createFile(Paths.get(GlobalVars.rootDir + "/debug.log"));
		Files.writeString(Paths.get(GlobalVars.rootDir + "/debug.log"), message + "\n", StandardOpenOption.APPEND);
	    }
	}
	catch (Exception e) {
	    // Do nothing, this isn't super important
	}
    }

    public static void logError(String message, BroadcastTarget p) {
	GlobalVars.errorLogged = true;
	if (p == null)
	    p = Broadcaster.getConsole();
	Broadcaster.broadcastSingle("§6[ERROR] " + message, p);
	if (GlobalVars.logErrors) {
	    try {
		String errMessage = "";
		errMessage += message + "\n";

		if (!Files.exists(Paths.get(GlobalVars.rootDir + "/error.log")))
		    Files.createFile(Paths.get(GlobalVars.rootDir + "/error.log"));
		Files.writeString(Paths.get(GlobalVars.rootDir + "/error.log"), errMessage, StandardOpenOption.APPEND);
	    }
	    catch (Exception e2) {
		// Also not super important
	    }
	}
    }
}
