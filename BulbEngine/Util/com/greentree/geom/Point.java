package com.greentree.geom;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Arrays;
import java.util.List;

import com.greentree.util.math.vector.float2f;

public class Point extends Shape {
	
	private float x, y;
	
	public Point() {
	}
	
	public Point(final float x, final float y) {
		this.x = x;
		this.y = y;
		trim();
	}
	
	protected void trim(){
		super.center = this;
	}
	
	@Override
	public void add(final float2f step) {
		x += step.getX();
		y += step.getY();
	}
	
	@Override
	public float distanse(final Point p) {
		return (float) Math.sqrt(Math.pow(p.getX() - getX(), 2) + Math.pow(p.getY() - getY(), 2));
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Point)) return false;
		final Point other = (Point) obj;
		if(abs(x - other.x) > 1E-9) return false;
		if(abs(y - other.y) > 1E-9) return false;
		return true;
	}
	
	@Override
	public Point getCenter() {
		return this;
	}

	@Override
	public List<Line> getLines() {
		return null;
	}

	@Override
	public List<Point> getPoints() {
		return Arrays.asList(this);
	}

	@Override
	public float getRadius() {
		return 0;
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	@Override
	public Point minPoint(final Point p) {
		return this;
	}

	@Override
	public void rotate(final Point c, final double ang) {
		x -= c.getX();
		y -= c.getY();
		final float x_ = x, y_ = y;
		x = (float) (x_ * cos(ang) - y_ * sin(ang));
		y = (float) (x_ * sin(ang) + y_ * cos(ang));
		x += c.getX();
		y += c.getY();
	}
	
	@Override
	public String toString() {
		return "[" + x + " " + y + "]";
	}

	@Override
	public void transleteX(final Translete t) {
		x = t.translete(x);
	}
	
	@Override
	public void transleteY(final Translete t) {
		y = t.translete(y);
	}
}
