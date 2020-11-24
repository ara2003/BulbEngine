package com.greentree.geom;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

import com.greentree.util.math.vector.float2f;

public class Line extends Shape {
	
	private final Point p1, p2;

	public Line(final float f, final float g, final float h, final float i) {
		this(new Point(f, g), new Point(h, i));
	}
	
	public Line(final Point p1, final Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Line other = (Line) obj;
		 if(!p1.equals(other.p1)) return false;
		 if(!p2.equals(other.p2)) return false;
		return true;
	}

	@Override
	public void add(final float2f step) {
		p1.add(step);
		p2.add(step);
	}
	
	@Override
	public List<Line> getLines() {
		return Arrays.asList(this);
	}

	private Line getNormal0(final Point p) {
		final Line l = new Line(p.getX(), p.getY(), p.getX() + p2.getX() - p1.getX(), p.getY() + p2.getY() - p1.getY());
		l.p2.rotate(l.p1, Math.PI / 2);
		return l;
	}

	@Override
	public List<Point> getPoints() {
		return Arrays.asList(p1, p2);
	}

	public float getX1() {
		return p1.getX();
	}

	public float getX2() {
		return p2.getX();
	}
	
	public float getY1() {
		return p1.getY();
	}

	public float getY2() {
		return p2.getY();
	}
	
	@Override
	public Point minPoint(final Point p) {
		Point f = GeomUtil.contact(this, getNormal0(p), false);
		if(f == null) f = GeomUtil.contact(getNormal0(p), this, false);
		if(f == null) {
			System.out.println(this);
			System.out.println(
					new Line(p.getX(), p.getY(), p.getX() + p2.getX() - p1.getX(), p.getY() + p2.getY() - p1.getY()));
			System.out.println(getNormal0(p));
			System.exit(1);
			return null;
		}
		if(f.getX() < Math.min(p1.getX(), p2.getX())) return p1.getX() < p2.getX() ? p1 : p2;
		if(f.getX() > Math.max(p1.getX(), p2.getX())) return p1.getX() > p2.getX() ? p1 : p2;
		if(f.getY() < Math.min(p1.getY(), p2.getY())) return p1.getY() < p2.getY() ? p1 : p2;
		if(f.getY() > Math.max(p1.getY(), p2.getY())) return p1.getY() > p2.getY() ? p1 : p2;
		return f;
	}
	
	@Override
	public void rotate(final Point p, final double ang) {
		p1.rotate(p, ang);
		p2.rotate(p, ang);
	}
	
	@Override
	public String toString() {
		return (int) p1.getX() + " " + (int) p1.getY() + " " + (int) p2.getX() + " " + (int) p2.getY();
	}

	public float2f getVector() {
		return new float2f(p1.getX() - p2.getX(), p1.getY() - p2.getY());
	}

	public float length() {
		float x = p1.getX() - p2.getX();
		float y = p1.getY() - p2.getY();
		return (float) Math.sqrt((x*x)+(y*y));
	}
}
