package com.greentree.engine.system.bootstrap;

import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;


public class InitOpenGL extends MultiBehaviour {

	@EditorData("clear color")
	private Color backColor;
	@EditorData("clear depth")
	private double depth;



	@Override
	protected void start() {
		Graphics.clearColor(backColor.r, backColor.g, backColor.b);
		Graphics.setClearDepth(depth);
	}

}
