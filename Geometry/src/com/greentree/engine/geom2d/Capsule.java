package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Matrix2f;
import org.joml.Vector2f;

/** @author Arseny Latyshev
 *
 * @see Shape2D */
public class Capsule extends Shape2D {
	
	protected final static byte PointInCapsule = Circle.PointInCircle;
	private final Point2D focus1, focus2;
	private final float radius;
	
	public Capsule(final float focusX, final float focusY, final float height, final float radius) {
		this(focusX, focusY, focusX, focusY + height, radius);
	}
	
	public Capsule(final float x1, final float y1, final float x2, final float y2, final float radius) {
		if(radius <= 0)throw new IllegalArgumentException("radius must be positive : " + radius);
		this.focus1 = new Point2D(x1, y1);
		this.focus2 = new Point2D(x2, y2);
		this.radius = radius;
	}
	
	@Override
	public Capsule add(final Vector2f step) {
		this.focus1.add(step);
		this.focus2.add(step);
		return this;
	}
	
	@Override
	public Point2D getCenter() {
		return GeomUtil2D.getCenter(Arrays.asList(this.focus1, this.focus2));
	}
	
	public Point2D getFocus1() {
		return this.focus1.clone();
	}
	
	@Override
	public List<Point2D> getPoints() {
		final Vector2f focus_vec = this.focus2.getRadiusVector().sub(this.focus1.getRadiusVector()).normalize(this.radius);
		focus_vec.mul(new Matrix2f().rotate((float) (Math.PI / 2)));
		final List<Point2D> points = new ArrayList<>(Capsule.PointInCapsule);
		points.add(new Point2D(this.focus1, focus_vec));
		final Matrix2f mat = new Matrix2f().rotate((float) (2 * Math.PI / Capsule.PointInCapsule));
		for(int i = 0; i < Capsule.PointInCapsule / 2; i++) {
			focus_vec.mul(mat);
			points.add(new Point2D(this.focus1, focus_vec));
		}
		for(int i = 0; i < Capsule.PointInCapsule / 2; i++) {
			focus_vec.mul(mat);
			points.add(new Point2D(this.focus2, focus_vec));
		}
		return points;
	}
	
	@Override
	public float getRadius() {
		return this.radius + this.focus1.distanse(this.focus2) / 2;
	}
	
	@Override
	public void rotate(final Point2D point, final double ang) {
		this.focus1.rotate(point, ang);
		this.focus2.rotate(point, ang);
	}
}
