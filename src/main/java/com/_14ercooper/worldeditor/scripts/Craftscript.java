package com._14ercooper.worldeditor.scripts;

import java.util.LinkedList;

import com._14ercooper.worldeditor.wrapper.Player;

public abstract class Craftscript {
    public abstract boolean perform(LinkedList<String> args, Player player, String label);
}
