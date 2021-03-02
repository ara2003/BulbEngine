package com.greentree.geom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Matrix2d;
import org.joml.Vector2f;

/** @author Arseny Latyshev
 *
 * @see Shape */
public class Capsule extends Shape {
	
	protected final static byte PointInCapsule = Circle.PointInCircle;
	private final Point focus1, focus2;
	private final float radius;
	
	public Capsule(final float focusX, final float focusY, final float height, final float radius) {
		focus1 = new Point(focusX, focusY);
		focus2 = new Point(focusX, focusY + height);
		this.radius = radius;
	}
	
	public Capsule(final float x1, final float y1, final float x2, final float y2, final float radius) {
		focus1 = new Point(x1, y1);
		focus2 = new Point(x2, y2);
		this.radius = radius;
	}
	
	@Override
	public void add(Vector2f step) {
		focus1.add(step);
		focus2.add(step);
	}
	
	@Override
	public Point getCenter() {
		return GeomUtil.getCenter(Arrays.asList(focus1, focus2));
	}
	
	public Point getFocus1() {
		return focus1.clone();
	}
	
	@Override
	public List<Point> getPoints() {
		final Vector2f focus_vec = focus2.getRadiusVector().sub(focus1.getRadiusVector());
		focus_vec.normalize(radius);
		focus_vec.mul(new Matrix2d().rotate(Math.PI / 2));
		final List<Point> p = new ArrayList<>(PointInCapsule);
		p.add(new Point(focus1, focus_vec));
		for(int i = 0; i < (PointInCapsule / 2); i++) {
			focus_vec.mul(new Matrix2d().rotate(Math.PI / 2));
			p.add(new Point(focus1, focus_vec));
		}
		for(int i = 0; i < (PointInCapsule / 2); i++) {
			focus_vec.mul(new Matrix2d().rotate(Math.PI / 2));
			p.add(new Point(focus2, focus_vec));
		}
		return p;
	}
	
	@Override
	public float getRadius() {
		return radius + (focus1.distanse(focus2) / 2);
	}
	
	@Override
	public void rotate(final Point point, final double ang) {
		focus1.rotate(point, ang);
		focus2.rotate(point, ang);
	}
}
