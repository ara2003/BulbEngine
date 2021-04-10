package com.greentree.engine.component.ui;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.opengl.Graphics;
import com.greentree.engine.Cameras;
import com.greentree.engine.core.GameSystem;


/** @author Arseny Latyshev */
public class Convas extends GameSystem {
	
	@Override
	protected void update() {
		for(final UIComponent renderable : this.getAllComponents(UIComponent.class)) {
			Cameras.getMainCamera().translateNotMove();
			renderable.render();
			Cameras.getMainCamera().unTranslateNotMove();
		}
	}
	
}
