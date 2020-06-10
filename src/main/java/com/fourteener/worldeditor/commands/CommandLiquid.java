package com.fourteener.worldeditor.commands;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.fourteener.worldeditor.main.GlobalVars;
import com.fourteener.worldeditor.main.Main;

public class CommandLiquid implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			GlobalVars.targetMask = new HashSet<Material>();
			for (String s : args) {
				GlobalVars.targetMask.add(Material.matchMaterial(s));
			}

			return true;
		} catch (Exception e) {
			Main.logError("Failed to set block mask.", sender);
			return false;
		}
	}
}
