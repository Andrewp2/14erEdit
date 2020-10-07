package com._14ercooper.worldeditor.wrapper;

public interface CommandSender extends BroadcastTarget {

    boolean dispatchCommand(String command);

}
