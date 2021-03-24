package com.greentree.engine.component;

import com.greentree.bulbgl.Renderable;
import com.greentree.engine.system.RenderSystem;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.system.NecessarilySystems;

/** @author Arseny Latyshev */
@NecessarilySystems({RenderSystem.class})
public abstract class AbstractRendenerComponent extends GameComponent implements Renderable {
	private static final long serialVersionUID = 1L;
	
	
	
}
