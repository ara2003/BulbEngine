package com.greentree.geom;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Shape {
	
	private static final int pointInCircle = 30;
	private final float radius;
	private final Point center;

	public Circle(final float x, final float y, final float r) {
		super();
		center = new Point(x, y);
		radius = r;
		trim();
	}

	@Override
	public float distanse(final Point p) {
		return Math.abs(p.distanse(getCenter()) - radius);
	}
	
	@Override
	public Point getCenter() {
		return center;
	}

	@Override
	public List<Point> getPoints() {
		final List<Point> p = new ArrayList<>(Circle.pointInCircle);
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
		return contact(new Line(getCenter().getX(), getCenter().getY(), p.getX(), p.getY())).get(0);
	}
	
	@Override
	public void rotate(final Point point, final double ang) {
		getCenter().rotate(point, ang);
	}
}
