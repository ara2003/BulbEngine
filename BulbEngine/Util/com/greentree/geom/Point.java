package com.greentree.geom;

import java.util.Arrays;
import java.util.List;

import com.greentree.util.math.vector.Matrix;
import com.greentree.util.math.vector.float2f;

public class Point extends Shape {
	
	private float2f p;
	
	public Point() {
		p = new float2f();
	}
	
	public Point(final float x, final float y) {
		p = new float2f(x, y);
	}
	
	@Override
	public void add(final float2f v) {
		p.additionEqual(v);
	}

	@Override
	public float distanse(final Point p) {
		return (float) Math.sqrt(Math.pow(p.getX() - getX(), 2) + Math.pow(p.getY() - getY(), 2));
	}

	@Override
	public List<Line> getLines() {
		return null;
	}

	@Override
	public List<Point> getPoints() {
		return Arrays.asList(this);
	}

	public float getX() {
		return p.getX();
	}
	
	public float getY() {
		return p.getY();
	}
	
	@Override
	public Point minPoint(final Point p) {
		return this;
	}

	@Override
	public void rotate(final Point c, final double ang) {
		p.additionEqual(c.p.multiply(-1));
		p.multiplyEqual(new Matrix(new double[][]{{(float) Math.cos(ang),-(float) Math.sin(ang)},
				{(float) Math.sin(ang),(float) Math.cos(ang)}}));
		p.additionEqual(c.p);
	}
	
	@Override
	public String toString() {
		return p.toString();
	}

	public void translete(T t) {
		
		Point o = t.translete(p.getX(), p.getY());
		this.p = o.p;
	}
}
