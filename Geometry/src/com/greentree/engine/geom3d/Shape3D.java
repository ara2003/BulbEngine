package com.greentree.engine.geom3d;

import com.greentree.engine.geom.Shape;

/** @author Arseny Latyshev */
public abstract class Shape3D implements Shape<AABB, Point3D, Shape3D> {
	
	@Override
	public float distanse(final Point3D p) {
		return 0;
	}
	
	@Override
	public AABB getAABB() {
		return new AABB(this);
	}
	
	@Override
	public Point3D getCenter() {
		return null;
	}
	
	@Override
	public float getRadius() {
		return 0;
	}
	
	@Override
	public boolean isIntersect(final Shape3D other) {
		return new AABB(this).isIntersect(new AABB(other));
	}
}
