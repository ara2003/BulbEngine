package com.greentree.geom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arseny Latyshev
 *
 * @see Shape
 */
public class Circle extends Shape {
	
	protected static final byte PointInCircle = 30;
	private final float radius;
	
	public Circle(final float x, final float y, final float r) {
		center = new Point(x, y);
		radius = r;
	}
	
	public Circle(Point point, int r) {
		center = point.clone();
		radius = r;
	}
	
	@Override
	public float distanse(final Point p) {
		return Math.abs(p.distanse(center) - radius);
	}
	
	@Override
	public List<Point> getPoints() {
		final List<Point> p = new ArrayList<>(Circle.PointInCircle+2);
		for(int i = 0; i < (Circle.PointInCircle+2); i++) {
			p.add(new Point((float) (center.getX() + (radius * Math.cos((2 * Math.PI * i)/ Circle.PointInCircle))),
					(float) (center.getY() + (radius * Math.sin((2 * Math.PI *i)/ Circle.PointInCircle)))));
		}
		return p;
	}
	
	@Override
	public float getRadius() {
		return radius;
	}
	
	@Override
	public Point minPoint(final Point p) {
		return new Point(p, p.getRadiusVector().scaleTo(getRadius()));
	}
	
	@Override
	public void rotate(final Point point, final double ang) {
		center.rotate(point, ang);
	}
	
	@Override
	public String toString() {
		return "Circle [radius=" + radius + ", center=" + center + "]";
	}
}
