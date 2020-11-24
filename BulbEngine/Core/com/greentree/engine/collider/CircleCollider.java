package com.greentree.engine.collider;

import com.greentree.geom.Circle;

public class CircleCollider extends Collider {
	
	public CircleCollider(final float r, final boolean triger) {
		super(triger);
		shape = new Circle(0, 0, r);
	}
}
