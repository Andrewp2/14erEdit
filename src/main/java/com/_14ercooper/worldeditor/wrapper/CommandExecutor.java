package com._14ercooper.worldeditor.wrapper;

public interface CommandExecutor {
    public boolean onCommand(CommandSender sender, String label, String[] args);
}
