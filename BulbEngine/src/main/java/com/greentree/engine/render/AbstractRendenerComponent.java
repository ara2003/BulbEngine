package com.greentree.engine.render;

import com.greentree.engine.component.Transform;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.StartGameComponent;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
public abstract class AbstractRendenerComponent extends StartGameComponent {

	protected Transform position;

	public abstract void render();

	@Override
	public void start() {
		position = this.getComponent(Transform.class);
	}

}
