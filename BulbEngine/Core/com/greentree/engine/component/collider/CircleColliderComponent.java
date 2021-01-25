package com.greentree.engine.component.collider;

import com.greentree.engine.component.util.EditorData;
import com.greentree.geom.Circle;

/** @author ara */
public final class CircleColliderComponent extends ColliderComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	public float radius;
	
	@Override
	public void start() {
		shape = new Circle(0, 0, radius);
		super.start();
	}
	
}
