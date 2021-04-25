package com.greentree.engine.system;

import com.greentree.common.time.Time;
import com.greentree.engine.Cameras;
import com.greentree.engine.core.GameSystem;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class ShowFPS extends GameSystem {
	
	@Override
	protected void update() {
		Graphics.enableBlead();
		Cameras.getMainCamera().translateAsWindow();
		Color.white.bind();
		Graphics.getFont().drawString(10, -20, "FPS: " + Time.getFps());
		Cameras.getMainCamera().untranslate();
		Graphics.disableBlead();
	}
	
}
