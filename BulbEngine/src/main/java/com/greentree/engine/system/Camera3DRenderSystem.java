package com.greentree.engine.system;

import com.greentree.engine.component.render.Camera3DRendenerComponent;
import com.greentree.engine.core.GameSystem;

/**
 * @author Arseny Latyshev
 *
 */
public class Camera3DRenderSystem extends GameSystem {
	
	@Override
	protected void update() {
		for(final Camera3DRendenerComponent renderable : this.getAllComponents(Camera3DRendenerComponent.class))
			renderable.render();
	}
}
