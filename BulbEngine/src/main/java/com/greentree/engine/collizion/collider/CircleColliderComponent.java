package com.greentree.engine.collizion.collider;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.component.EditorData;
import com.greentree.engine.geom2d.Circle;
import com.greentree.engine.geom2d.Shape2D;

/** @author ara */
public final class CircleColliderComponent extends ColliderComponent {
	
	@EditorData
	public float radius;
	
	@Override
	protected Shape2D generateShape() {
		return new Circle(0, 0, this.radius);
	}
}
