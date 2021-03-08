package com.greentree.engine.geom2d;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiLine extends Shape2D {
	
	private final List<Point2D> points;
	
	public MultiLine(final List<Point2D> points) {
		this.points = new CopyOnWriteArrayList<>(points);
	}
	
	@Override
	public List<Line> getLines() {
		List<Line> lines = super.getLines();
		lines.remove(0);
		return lines;
	}
	@Override
	public List<Point2D> getPoints() {
		return points;
	}
	@Override
	public void rotate(final Point2D point, final double ang) {
		for(final Point2D p : points) p.rotate(point, ang);
	}
}
