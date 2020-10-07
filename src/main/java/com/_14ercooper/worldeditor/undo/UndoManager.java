package com._14ercooper.worldeditor.undo;

import java.util.ArrayList;
import java.util.List;

import com._14ercooper.worldeditor.wrapper.Player;

public class UndoManager {
    private static List<Undo> undoList = new ArrayList<Undo>();

    public static Undo getUndo(Player owner) {
	for (Undo u : undoList) {
	    if (u.owner.equals(owner))
		return u;
	}
	Undo u = new Undo(owner);
	undoList.add(u);
	return u;
    }
}
