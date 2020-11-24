package com.greentree.geom;

import java.util.Arrays;
import java.util.List;

import com.greentree.util.math.vector.float2f;

@Deprecated
public class Capsule extends Shape {
	
	private final Point p1, p2;
	private final float radius;
	
	public Capsule(final float x1, final float y1, final float x2, final float y2, final float radius) {
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
		this.radius = radius;
	}
	
	@Override
	public void add(final float2f step) {
		p1.add(step);
		p2.add(step);
	}
	
	@Override
	public Point getCenter() {
		return GeomUtil.getCenter(Arrays.asList(p1, p2));
	}

	@Override
	public List<Point> getPoints() {
		return null;
	}
	
	@Override
	public float getRadius() {
		return radius + p1.distanse(p2) / 2;
	}
	
	@Override
	public void rotate(final Point point, final double ang) {
		p1.rotate(point, ang);
		p2.rotate(point, ang);
	}
}
