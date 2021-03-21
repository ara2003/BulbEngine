package com.greentree.engine.component;

import com.greentree.bulbgl.Renderable;
import com.greentree.engine.system.RenderSystem;
import com.greentree.engine.system.NecessarilySystems;

/** @author Arseny Latyshev */
@NecessarilySystems({RenderSystem.class})
@RequireComponent({Transform.class})
public abstract class RendenerComponent extends GameComponent implements Renderable {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public final void update() {
	}
	
}
