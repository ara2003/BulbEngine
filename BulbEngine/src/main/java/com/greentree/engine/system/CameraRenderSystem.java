package com.greentree.engine.system;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.opengl.Graphics;
import com.greentree.engine.Cameras;
import com.greentree.engine.component.render.CameraRendenerComponent;
import com.greentree.engine.core.GameSystem;

/** @author Arseny Latyshev */
public class CameraRenderSystem extends GameSystem {
	
	@Override
	protected void update() {
		for(final CameraRendenerComponent renderable : this.getAllComponents(CameraRendenerComponent.class)) {
			Cameras.getMainCamera().translate();
			renderable.render();
			Cameras.getMainCamera().untranslate();
		}
	}
}
