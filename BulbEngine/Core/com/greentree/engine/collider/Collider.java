package com.greentree.engine.collider;

import com.greentree.geom.Point;
import com.greentree.geom.Shape;
import com.greentree.util.math.vector.float2f;

public abstract class Collider {
	
	public Shape shape;
	protected boolean triger;
	
	public Collider(final boolean triger) {
		this.triger = triger;
	}
	
	public void add(final float2f step) {
		shape.add(step);
	}

	public boolean contact(final Collider other) {
		return !shape.contact(other.shape).isEmpty();
	}
	
	public Point minPoint(final Point point) {
		return shape.minPoint(point);
	}
	
	public void moveTo(final float x, final float y) {
		shape.moveTo(x, y);
	}
}
