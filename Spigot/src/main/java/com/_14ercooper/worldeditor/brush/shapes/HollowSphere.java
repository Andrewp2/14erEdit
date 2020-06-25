package com._14ercooper.worldeditor.brush.shapes;

import java.util.ArrayList;
import java.util.List;

import com._14ercooper.worldeditor.blockiterator.BlockIterator;
import com._14ercooper.worldeditor.brush.BrushShape;
import com._14ercooper.worldeditor.main.*;
import com._14ercooper.worldeditor.operations.Operator;

public class HollowSphere extends BrushShape {

	@Override
	public BlockIterator GetBlocks(List<Double> args, double x, double y, double z) {
		try {
			List<String> argList = new ArrayList<String>();
			argList.add(Integer.toString((int) x));
			argList.add(Integer.toString((int) y));
			argList.add(Integer.toString((int) z));
			argList.add(Integer.toString((int) (double) args.get(0)));
			argList.add(Integer.toString((int) (double) args.get(1)));
			argList.add(args.get(2).toString());
			return GlobalVars.iteratorManager.getIterator("sphere").newIterator(argList);
		} catch (Exception e) {
			Main.logError("Could not parse hollow sphere. Did you provide a radius, thickness, and correction?", Operator.currentPlayer);
			return null;
		}
	}

	@Override
	public double GetArgCount() {
		return 3;
	}

}