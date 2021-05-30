package com.greentree.engine.collizion.collider;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.geom2d.Capsule;
import com.greentree.engine.geom2d.Shape2D;

/** @author ara */
public final class CapsuleColliderComponent extends ColliderComponent {

	@EditorData
	public float radius, height, x, y;

	@Override
	protected Shape2D generateShape() {
		return new Capsule(0, 0, height, radius);
	}

	@Override
	public float getDeltaX() {
		return x;
	}

	@Override
	public float getDeltaY() {
		return y;
	}

}
