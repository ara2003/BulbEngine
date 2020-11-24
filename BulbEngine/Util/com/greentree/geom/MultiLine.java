package com.greentree.geom;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greentree.util.math.vector.float2f;

public class MultiLine extends Shape {
	
	private final List<Point> points;

	public MultiLine(final List<Point> points) {
		this.points = new CopyOnWriteArrayList<>(points);
	}
	
	@Override
	public void add(final float2f step) {
		for(final Point p : points) p.add(step);
	}
	
	@Override
	public List<Point> getPoints() {
		return points;
	}
	
	@Override
	public boolean isClosed() {
		return false;
	}
	
	@Override
	public void rotate(final Point point, final double ang) {
		for(final Point p : points) p.rotate(point, ang);
	}

}
