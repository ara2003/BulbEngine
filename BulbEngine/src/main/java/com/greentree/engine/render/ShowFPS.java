package com.greentree.engine.render;

import com.greentree.engine.core.builder.RequireSystems;
import com.greentree.engine.core.node.GameSystem.MultiBehaviour;
import com.greentree.engine.system.TimeSystem;
import com.greentree.engine.util.Cameras;
import com.greentree.engine.util.Time;
import com.greentree.graphics.Graphics;

@RequireSystems(TimeSystem.class)
/** @author Arseny Latyshev */
public class ShowFPS extends MultiBehaviour {

	@Override
	public void update() {
		Cameras.getMainCamera().translateAsWindow();
		Graphics.enableBlead();
		Graphics.getFont().drawString(10, -20, String.format("FPS: %d", Time.getFps()));
		Graphics.disableBlead();
		Cameras.getMainCamera().untranslate();
	}

}
