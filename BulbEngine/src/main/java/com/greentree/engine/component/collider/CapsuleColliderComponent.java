package com.greentree.engine.component.collider;

import com.greentree.engine.component.EditorData;
import com.greentree.engine.geom2d.Capsule;
import com.greentree.engine.geom2d.Shape2D;

/** @author ara */
public final class CapsuleColliderComponent extends ColliderComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	public float radius, height, x, y;
	
	@Override
	protected Shape2D generateShape() {
		return new Capsule(0, 0, this.height, this.radius);
	}
	
	@Override
	public float getX() {
		return this.x + super.getX();
	}
	
	@Override
	public float getY() {
		return this.y + super.getY();
	}
}
