package com.greentree.engine2d.render;

import com.greentree.engine.Cameras;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
public class CameraRenderSystem extends MultiBehaviour {

	@Override
	public void update() {
		for(final CameraRendenerComponent renderable : this.getAllComponents(CameraRendenerComponent.class)) {
			Cameras.getMainCamera().translateAsWorld();
			renderable.render();
			Cameras.getMainCamera().untranslate();
		}
	}
}
