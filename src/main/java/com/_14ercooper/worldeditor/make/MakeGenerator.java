package com._14ercooper.worldeditor.make;

import java.util.Map;

import com._14ercooper.worldeditor.wrapper.Player;

public abstract class MakeGenerator {
    public abstract boolean make(Player p, Map<String, String> args, double x, double y, double z);
}
