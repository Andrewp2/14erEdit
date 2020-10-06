package com._14ercooper.worldeditor.make;

import java.util.Map;

public abstract class MakeGenerator {
    public abstract boolean make(Player p, Map<String, String> args, double x, double y, double z);
}
