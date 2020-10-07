package com._14ercooper.worldeditor.wrapper;

public class CallbackManager {
    public static void registerNowCallback(Object e) {
	if (!(e instanceof CallbackEvent)) {
	    return;
	}
	CallbackEvent event = (CallbackEvent) e;
	
	// TODO
    }
}
