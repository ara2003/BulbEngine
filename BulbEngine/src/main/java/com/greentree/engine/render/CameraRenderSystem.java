package com.greentree.engine.render;

import com.greentree.engine.Cameras;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.system.GroupSystem;

/** @author Arseny Latyshev */
@GroupSystem("graphics")
public class CameraRenderSystem extends GameSystem {
	
	@Override
	public void update() {
		for(final CameraRendenerComponent renderable : this.getAllComponents(CameraRendenerComponent.class)) {
			Cameras.getMainCamera().translateAsWorld();
			renderable.render();
			Cameras.getMainCamera().untranslate();
		}
	}
}
