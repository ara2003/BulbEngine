package com.greentree.engine.component;

import com.greentree.engine.component.util.necessarilySystem;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.system.ColliderSystem;
import com.greentree.geom.Shape;

@necessarilySystem(ColliderSystem.class)
public abstract class ColliderComponent extends GameComponent {

	private static final long serialVersionUID = 1L;
	protected Transform t;

	public abstract Shape getShape();
	public abstract boolean isTriger();
}
