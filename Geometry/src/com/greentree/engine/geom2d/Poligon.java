package com.greentree.engine.geom2d;

import java.util.Arrays;
import java.util.List;

public class Poligon extends Shape2D {
	
	protected final Point2D[] point;
	
	public Poligon(final float... point) {
		this(GeomUtil2D.getPoint(point));
	}
	
	public Poligon(final Point2D... point) {
		this.point = point;
	}
	
	@Override
	public List<Point2D> getPoints() {
		return Arrays.asList(point);
	}
	
	@Override
	public void rotate(final Point2D p, final double d) {
		for(final Point2D point : point) point.rotate(p, d);
	}

	@Override
	public Shape2D add(float x, float y) {
		for(final Point2D point : point) point.add(x, y);
		return this;
	}
}
