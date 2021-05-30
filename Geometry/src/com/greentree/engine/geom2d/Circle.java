package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.greentree.common.math.Mathf;

/**
 * @author Arseny Latyshev
 *
 * @see Shape2D
 */
public final class Circle extends Shape2D {

	protected static final int PointInCircle = 30;
	protected Point2D center;
	private final float radius;

	public Circle(final float x, final float y, final float r) {
		center = new Point2D(x, y);
		radius = r;
	}
	
	@Override
	public int getPointsSize() {
		return PointInCircle;
	}
	
	@Override
	public boolean isInside(Point2D p) {
		return center.distanseSqr(p) <= radius*radius;
	}
	
	public Circle(Point2D point, float r) {
		center = point.clone();
		radius = r;
	}

	public Circle(Vector2f point, float radius) {
		this(new Point2D(point), radius);
	}

	@Override
	public Shape2D add(float x, float y) {
		center.add(x, y);
		return this;
	}

	@Override
	public float distanse(final Point2D p) {
		return Math.abs(p.distanse(center) - radius);
	}

	@Override
	public AABB getAABB() {
		return new AABB(center.x-radius, center.y-radius, 2*radius, 2*radius);
	}
	
	@Override
	public List<Point2D> getPoints() {
		final List<Point2D> points = new ArrayList<>(Circle.PointInCircle);
		for(int i = 0; i < 2*Circle.PointInCircle; i++) {
			double d = Math.PI * i / Circle.PointInCircle;
			points.add(new Point2D(center.getX() + radius * Mathf.cos(d), center.getY() + radius * Mathf.sin(d)));
		}
		return points;
	}

	@Override
	public float getRadius() {
		return radius;
	}

	@Override
	public boolean isIntersect(Shape2D other) {
		if(other instanceof Circle) {
			Circle circle = (Circle)other;
			float r = radius + circle.radius;
			return circle.center.distanseSqr(center) <= r*r;
		}
		return super.isIntersect(other);
	}

	@Override
	public Point2D minPoint(final Point2D p) {
		return new Point2D(center, p.getRadiusVector().sub(center.getRadiusVector()).normalize(getRadius()));
	}

	@Override
	public void rotate(final Point2D point, final double ang) {
		center.rotate(point, ang);
	}

	@Override
	public String toString() {
		return "Circle [radius=" + radius + ", center=" + center + "]";
	}
}
