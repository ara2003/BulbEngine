package com.greentree.engine.component;

import com.greentree.bulbgl.Renderable;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.UpdatingGameComponent;
import com.greentree.engine.core.system.NecessarilySystems;
import com.greentree.engine.system.RenderSystem;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
@NecessarilySystems({RenderSystem.class})
public abstract class AbstractRendenerComponent extends UpdatingGameComponent implements Renderable {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void update() {
	}
	
}
