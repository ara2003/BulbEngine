package com.greentree.engine.component;

import com.greentree.engine.necessarilySystems;
import com.greentree.engine.opengl.Renderable;
import com.greentree.engine.system.RenderSystem;


/**
 * @author Arseny Latyshev
 *
 */
@necessarilySystems({RenderSystem.class})
public abstract class RendenerComponent extends offsetGameComponent  implements Renderable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected final void update() {
		super.update();
	}
	
}
