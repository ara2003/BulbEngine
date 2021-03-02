package com.greentree.geom;

import java.util.Arrays;
import java.util.List;

public class Poligon extends Shape {
	
	protected final Point[] point;
	
	public Poligon(final float... point) {
		this(GeomUtil.getPoint(point));
	}
	
	public Poligon(final Point... point) {
		this.point = point;
	}
	
	@Override
	public List<Point> getPoints() {
		return Arrays.asList(point);
	}
	
	@Override
	public void rotate(final Point p, final double d) {
		for(final Point point : point) point.rotate(p, d);
	}
}
