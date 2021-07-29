package com.greentree.engine2d.render;

import com.greentree.engine.core.node.GameSystem.MultiBehaviour;
import com.greentree.engine.util.Cameras;

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
