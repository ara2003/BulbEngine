package com.greentree.engine.render;

import com.greentree.engine.component.Transform;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.object.GameComponent;

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
