package com.greentree.engine.render;

import com.greentree.common.time.Time;
import com.greentree.engine.Cameras;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class ShowFPS extends MultiBehaviour {

	@Override
	public void update() {
		Cameras.getMainCamera().translateAsWindow();
		Graphics.enableBlead();
		Color.white.bind();
		Graphics.getFont().drawString(10, -20, String.format("FPS: %d", Time.getFps()));
		Graphics.disableBlead();
		Cameras.getMainCamera().untranslate();
	}

}
