package com.greentree.engine.render;

import com.greentree.engine.Windows;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;


public class InitWindow extends MultiBehaviour {

	@EditorData(required = true)
	private int width, height;
	@EditorData(required = true)
	private String title;// = "blub window";
	@EditorData(required = true)
	private boolean fullscreen;

	@Override
	protected void start() {
		Windows.createWindow(title, width, height, fullscreen);
	}

}
