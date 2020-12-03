package com.greentree.geom;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiLine extends Shape {

	private final List<Point> points;
	
	public MultiLine(final List<Point> points) {
		this.points = new CopyOnWriteArrayList<>(points);
	}

	@Override
	public List<Point> getPoints() {
		return points;
	}
	@Override
	public List<Line> getLines() {
		List<Line> lines = super.getLines();
		lines.remove(0);
		return lines;
	}
	@Override
	public void rotate(final Point point, final double ang) {
		for(final Point p : points) p.rotate(point, ang);
	}
}
