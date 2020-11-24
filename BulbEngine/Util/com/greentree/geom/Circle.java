package com.greentree.geom;

import java.util.ArrayList;
import java.util.List;

import com.greentree.util.math.vector.float2f;

public class Circle extends Shape {

	private static final int pointInCircle = 30;
	private final Point center;
	private final float radius;
	
	public Circle(final float x, final float y, final float r) {
		center = new Point(x, y);
		radius = r;
	}

	@Override
	public void add(final float2f step) {
		center.add(step);
	}

	@Override
	public float distanse(final Point p) {
		return Math.abs(p.distanse(center) - radius);
	}

	@Override
	public Point getCenter() {
		return center;
	}
	
	@Override
	public List<Point> getPoints() {
		final List<Point> p = new ArrayList<Point>(Circle.pointInCircle);
		for(int i = 0; i < Circle.pointInCircle; i++)
			p.add(new Point((float) (center.getX() + radius * Math.cos(2 * Math.PI / Circle.pointInCircle * i)),
					(float) (center.getY() + radius * Math.sin(2 * Math.PI / Circle.pointInCircle * i))));
		return p;
	}
	
	@Override
	public float getRadius() {
		return radius;
	}
	
	@Override
	public Point minPoint(final Point p) {
		return contact(new Line(center.getX(), center.getY(), p.getX(), p.getY())).get(0);
	}

	@Override
	public void rotate(final Point point, final double ang) {
		center.rotate(point, ang);
	}
}
