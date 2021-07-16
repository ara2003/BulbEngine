package com.greentree.engine;

import com.greentree.common.logger.Log;
import com.greentree.graphics.window.SimpleWindow;

/** @author Arseny Latyshev */
public class Windows {

	static SimpleWindow window;

	public static void createWindow(String title, int width, int height, boolean fullscreen){
		if(window != null);
		window = new SimpleWindow(title, width, height, true, fullscreen);
		Log.info("Starting window " + width + "x" + height);
	}

	public static SimpleWindow getWindow() {
		//		if(Windows.window == null) {
		//			final int     width      = Properties.getProperty("window.width").notNull().toInt();
		//			final int     height     = Properties.getProperty("window.height").notNull().toInt();
		//			final boolean fullscreen = Properties.getProperty("window.fullscreen").notNull().toBoolean();
		//			window = new SimpleWindow(Properties.getOrDefault("window.title", "blub window"), width, height, true, fullscreen);
		//			Log.info("Starting display " + width + "x" + height);
		//		}
		return Windows.window;
	}

}
