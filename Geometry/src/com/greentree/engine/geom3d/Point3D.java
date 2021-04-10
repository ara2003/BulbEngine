package com.greentree.engine.geom3d;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector3f;

import com.greentree.engine.geom.Point;

/**
 * @author Arseny Latyshev
 *
 */
public class Point3D extends Shape3D implements Point<AABB, Point3D, Shape3D> {
	
	public float x, y, z;
	
	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(Point3D point) {
		x = point.x;
		y = point.y;
		z = point.z;
	}
	
	@Override
	public Point3D clone() {
		return new Point3D(this);
	}
	
	@Override
	public float distanseSqr(Point3D p) {
		float dx = p.x - x;
		float dy = p.y - y;
		float dz = p.z - z;
		return (dx * dx) + (dy * dy) + (dz * dz);
	}
	
	
	@Override
	public Point3D getCenter() {
		return this;
	}
	
	@Override
	public List<Point3D> getPoints() {
		return Arrays.asList(clone());
	}
	public Vector3f getRadiusVector() {
		return new Vector3f(x, y, z);
	}
	
	@Override
	public boolean isIntersect(Shape3D other) {
		return false;
	}

	@Override
	public void moveTo(Point3D p) {
	}
}
