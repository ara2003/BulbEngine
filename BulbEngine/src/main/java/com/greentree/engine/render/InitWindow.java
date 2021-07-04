package com.greentree.engine.render;

import com.greentree.engine.Windows;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;


public class InitWindow extends MultiBehaviour {

	@Required
	@EditorData
	private int width, height;
	@Required
	@EditorData
	private String title;
	@Required
	@EditorData
	private boolean fullscreen;

	@Override
	protected void start() {
		Windows.createWindow(title, width, height, fullscreen);
		Windows.getWindow().makeCurrent();
	}

}
