package com.greentree.engine.component;

import com.greentree.engine.necessarily;
import com.greentree.engine.component.util.offsetGameComponent;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.RenderSystem;
import com.greentree.geom.AABB;


/**
 * @author Arseny Latyshev
 *
 */
@necessarily({RenderSystem.class})
public abstract class RendenerComponent extends offsetGameComponent {
	private static final long serialVersionUID = 1L;

	public abstract void draw(SGL gl);
	protected abstract AABB getAABB();
	
	
	
}
