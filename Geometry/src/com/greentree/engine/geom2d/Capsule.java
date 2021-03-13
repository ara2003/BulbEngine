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
		focus1 = new Point2D(focusX, focusY);
		focus2 = new Point2D(focusX, focusY + height);
		this.radius = radius;
	}
	
	public Capsule(final float x1, final float y1, final float x2, final float y2, final float radius) {
		focus1 = new Point2D(x1, y1);
		focus2 = new Point2D(x2, y2);
		this.radius = radius;
	}
	
	@Override
	public Capsule add(@SuppressWarnings("exports") Vector2f step) {
		focus1.add(step);
		focus2.add(step);
		return this;
	}
	
	@Override
	public Point2D getCenter() {
		return GeomUtil2D.getCenter(Arrays.asList(focus1, focus2));
	}
	
	public Point2D getFocus1() {
		return focus1.clone();
	}
	
	@Override
	public List<Point2D> getPoints() {
		Vector2f focus_vec = focus2.getRadiusVector().sub(focus1.getRadiusVector()).normalize(radius);
		focus_vec.mul(new Matrix2f().rotate((float) (Math.PI / 2)));
		final List<Point2D> p = new ArrayList<>(PointInCapsule);
		p.add(new Point2D(focus1, focus_vec));
		Matrix2f mat = new Matrix2f().rotate((float) (2 * Math.PI / PointInCapsule));
		for(int i = 0; i < (PointInCapsule / 2); i++) {
			focus_vec.mul(mat);
			p.add(new Point2D(focus1, focus_vec));
		}
		for(int i = 0; i < (PointInCapsule / 2); i++) {
			focus_vec.mul(mat);
			p.add(new Point2D(focus2, focus_vec));
		}
		return p;
	}
	
	@Override
	public float getRadius() {
		return radius + (focus1.distanse(focus2) / 2);
	}
	
	@Override
	public Shape2D rotate(final Point2D point, final double ang) {
		focus1.rotate(point, ang);
		focus2.rotate(point, ang);
		return this;
	}

	@Override
	public Shape2D add(float x, float y) {
		return add(new Vector2f(x, y));
	}
}
