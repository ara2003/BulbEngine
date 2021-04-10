package com.greentree.engine;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.WindowI;
import com.greentree.bulbgl.glfw.BulBGLFWMode;
import com.greentree.bulbgl.input.Input;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.Properties;

/** @author Arseny Latyshev */
public class Windows {
	
	private static WindowI window;
	
	public static WindowI getWindow() {
		if(Windows.window == null) {
			BulbGL.init(new BulBGLFWMode());
			
			final int     width      = Integer.parseInt(Properties.getPropertyNotNull("window.width"));
			final int     height     = Integer.parseInt(Properties.getPropertyNotNull("window.height"));
			final boolean fullscreen = Boolean.parseBoolean(Properties.getPropertyNotNull("window.fullscreen"));
			Windows.window = BulbGL.getWindow(Properties.getOrDefault("window.title", "blub window"), width, height, fullscreen);
			Log.info("Starting display " + width + "x" + height);
			
			Input.setWindow(Windows.window);
			Input.setBulbInput(BulbGL.getInput());
		}
		return Windows.window;
	}
	
}
