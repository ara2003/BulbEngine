package com.greentree.engine.component.ui;

import com.greentree.engine.Cameras;
import com.greentree.engine.core.GameSystem;
import com.greentree.graphics.Graphics;


/** @author Arseny Latyshev */
public class Convas extends GameSystem {
	
	@Override
	protected void update() {
		Graphics.enableBlead();
		Cameras.getMainCamera().translateAsCamera();
		for(final UIComponent renderable : this.getAllComponents(UIComponent.class)) {
			renderable.render();
		}
		Cameras.getMainCamera().untranslate();
		Graphics.disableBlead();
	}
	
}
