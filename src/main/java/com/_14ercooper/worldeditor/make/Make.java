package com._14ercooper.worldeditor.make;

import java.util.HashMap;
import java.util.Map;

import com._14ercooper.worldeditor.main.Main;
import com._14ercooper.worldeditor.wrapper.Player;

public class Make {

    static Map<String, MakeGenerator> generators = new HashMap<String, MakeGenerator>();

    public static boolean make(Player player, String type, Map<String, String> tags) {
	if (!generators.containsKey(type)) {
	    Main.logError("Make generator not found: " + type, player);
	    return false;
	}
	try {
	    boolean returnVal = generators.get(type).make(player, tags, player.getLocation().getX(),
		    player.getLocation().getY(), player.getLocation().getZ());

	    return returnVal;
	}
	catch (Exception e) {
	    Main.logError("Error performing make. Please check your syntax.", player);
	    return false;
	}
    }
}
