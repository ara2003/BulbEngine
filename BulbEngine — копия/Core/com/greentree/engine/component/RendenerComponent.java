package com.greentree.engine.component;

import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.object.necessarily;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.RenderSystem;


/**
 * @author Arseny Latyshev
 *
 */
@necessarily({RenderSystem.class})
public abstract class RendenerComponent extends GameComponent {
	private static final long serialVersionUID = 1L;

	public abstract void draw(SGL gL);
	
	
	
}
