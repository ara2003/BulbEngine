package com.greentree.engine.component.render;

import com.greentree.engine.component.Transform;
import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.component.RequireComponent;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
public abstract class AbstractRendenerComponent extends GameComponent {
	
	protected Transform position;
	
	@Override
	protected void start() {
		this.position = this.getComponent(Transform.class);
	}
	
	public abstract void render();
	
}
