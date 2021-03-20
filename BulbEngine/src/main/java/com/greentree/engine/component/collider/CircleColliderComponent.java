package com.greentree.engine.component.collider;

import com.greentree.engine.component.EditorData;
import com.greentree.engine.geom2d.Circle;
import com.greentree.engine.geom2d.Shape2D;

/** @author ara */
public final class CircleColliderComponent extends ColliderComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	public float radius;
	
	@Override
	protected Shape2D generateShape() {
		return new Circle(0, 0, radius);
	}
}
