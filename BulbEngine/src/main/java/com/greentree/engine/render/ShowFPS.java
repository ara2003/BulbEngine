package com.greentree.engine.render;

import com.greentree.common.time.Time;
import com.greentree.engine.Cameras;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.system.GroupSystem;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
@GroupSystem(value="graphics", priority=Byte.MAX_VALUE)
public class ShowFPS extends GameSystem {
	
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
