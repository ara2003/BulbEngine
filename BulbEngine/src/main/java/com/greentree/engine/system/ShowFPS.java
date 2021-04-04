package com.greentree.engine.system;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.opengl.Graphics;
import com.greentree.common.time.Time;
import com.greentree.engine.core.GameSystem;


/** @author Arseny Latyshev */
public class ShowFPS extends GameSystem {
	
	@Override
	protected void update() {
		Graphics.setColor(Color.white);
		Graphics.drawString("FPS: " + Time.getFps(), 10, 10);
	}
	
}
