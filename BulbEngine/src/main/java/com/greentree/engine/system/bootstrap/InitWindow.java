package com.greentree.engine.system.bootstrap;

import com.greentree.engine.Game;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.object.GameSystem.MultiBehaviour;
import com.greentree.engine.util.Windows;


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
		Windows.getWindow().setWindowCloseCallback(() -> {
			Game.exit();
		});
		Windows.getWindow().makeCurrent();
	}

}
