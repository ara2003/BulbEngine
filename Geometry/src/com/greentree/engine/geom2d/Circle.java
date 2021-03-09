package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arseny Latyshev
 *
 * @see Shape2D
 */
public class Circle extends Shape2D {
	
	protected static final byte PointInCircle = 30;
	private final float radius;
	
	public Circle(final float x, final float y, final float r) {
		center = new Point2D(x, y);
		radius = r;
	}
	
	public Circle(Point2D point, int r) {
		center = point.clone();
		radius = r;
	}
	
	@Override
	public boolean isIntersect(Shape2D other) {
		if(other instanceof Circle) {
			Circle circle = (Circle) other;
			float r = radius + circle.radius;
			return circle.center.distanseSqr(center) < r*r;
		}
		return super.isIntersect(other);
	}
	
	@Override
	public float distanse(final Point2D p) {
		return Math.abs(p.distanse(center) - radius);
	}
	
	@Override
	public List<Point2D> getPoints() {
		final List<Point2D> p = new ArrayList<>(Circle.PointInCircle+2);
		for(int i = 0; i < (Circle.PointInCircle+2); i++) {
			p.add(new Point2D((float) (center.getX() + (radius * Math.cos((2 * Math.PI * i)/ Circle.PointInCircle))),
					(float) (center.getY() + (radius * Math.sin((2 * Math.PI *i)/ Circle.PointInCircle)))));
		}
		return p;
	}
	
	@Override
	public float getRadius() {
		return radius;
	}
	
	@Override
	public Point2D minPoint(final Point2D p) {
		return new Point2D(p, p.getRadiusVector().normalize(getRadius()));
	}
	
	@Override
	public Shape2D rotate(final Point2D point, final double ang) {
		center.rotate(point, ang);
		return this;
	}
	
	@Override
	public String toString() {
		return "Circle [radius=" + radius + ", center=" + center + "]";
	}

	@Override
	public Shape2D add(float x, float y) {
		center.add(x, y);
		return this;
	}
}
