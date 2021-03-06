package com._14ercooper.worldeditor.operations.operators.logical;

import com._14ercooper.worldeditor.main.*;
import com._14ercooper.worldeditor.operations.Operator;
import com._14ercooper.worldeditor.operations.operators.Node;

public class IfNode extends Node {

    public Node arg1, arg2, arg3;

    public IfNode newNode() {
	IfNode node = new IfNode();
	try {
	    node.arg1 = GlobalVars.operationParser.parsePart();
	    node.arg2 = GlobalVars.operationParser.parsePart();
	    node.arg3 = GlobalVars.operationParser.parsePart();
	}
	catch (Exception e) {
	    Main.logError("Error creating if node. Please check your syntax.", Operator.currentPlayer);
	    return null;
	}
	if (node.arg2 == null) {
	    Main.logError(
		    "Error creating if node. At least a condition and on-true operator are required, but are not provided.",
		    Operator.currentPlayer);
	}
	return node;
    }

    public boolean performNode() {
	try {
	    boolean isTrue = arg1.performNode();
	    boolean toReturn = false;
	    if (isTrue) {
		toReturn = arg2.performNode();
	    }
	    if (arg3 == null) {
		return toReturn;
	    }
	    if (arg3 instanceof ElseNode && !isTrue) {
		toReturn = arg3.performNode();
	    }
	    if (!(arg3 instanceof ElseNode)) {
		toReturn = arg3.performNode() && toReturn;
	    }
	    return toReturn;
	}
	catch (Exception e) {
	    Main.logError("Error performing if node. Please check your syntax.", Operator.currentPlayer);
	    return false;
	}
    }

    public int getArgCount() {
	return 3;
    }
}
