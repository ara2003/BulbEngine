package com.greentree.engine;

import com.greentree.common.logger.Log;
import com.greentree.engine.core.Properties;
import com.greentree.graphics.Window;
import com.greentree.graphics.window.SimpleWindow;

/** @author Arseny Latyshev */
public class Windows {
	
	static SimpleWindow window;
	
	public static Window getWindow() {
		if(Windows.window == null) {
			final int     width      = Integer.parseInt(Properties.getPropertyNotNull("window.width"));
			final int     height     = Integer.parseInt(Properties.getPropertyNotNull("window.height"));
			final boolean fullscreen = Boolean.parseBoolean(Properties.getPropertyNotNull("window.fullscreen"));
			window = new SimpleWindow(Properties.getOrDefault("window.title", "blub window"), width, height, true, fullscreen);
			Log.info("Starting display " + width + "x" + height);
		}
		return Windows.window;
	}
	
}
