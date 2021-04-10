package com.greentree.engine.geom2d;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.joml.Vector2f;

public class Line extends Shape2D {
	
	private final Point2D p1, p2;
	
	public Line(final float f, final float g, final float h, final float i) {
		this(new Point2D(f, g), new Point2D(h, i));
	}
	
	public Line(final Point2D p1, final Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	@Override
	protected Line clone() {
		return new Line(p1.clone(), p2.clone());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Line)) return false;
		Line other = (Line) obj;
		if(!Objects.equals(p1, other.p1)) {
			return false;
		}
		if(!Objects.equals(p2, other.p2)) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Line> getLines() {
		return Arrays.asList(this);
	}
	
	public Point2D getP1() {
		return p1;
	}
	
	
	public Point2D getP2() {
		return p2;
	}
	
	@Override
	public List<Point2D> getPoints() {
		return Arrays.asList(p1, p2);
	}
	
	
	public Vector2f getVector() {
		return new Vector2f(p1.getX() - p2.getX(), p1.getY() - p2.getY());
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((p1 == null) ? 0 : p1.hashCode());
		result = (prime * result) + ((p2 == null) ? 0 : p2.hashCode());
		return result;
	}
	
	public float length() {
		final float x = p1.getX() - p2.getX();
		final float y = p1.getY() - p2.getY();
		return (float) Math.sqrt((x * x) + (y * y));
	}
	
	@Override
	public Point2D minPoint(final Point2D p) {
		float a = p2.getX() - p1.getX();
		float b = p2.getY() - p1.getY();
		float c = (p1.getX() * p2.getY()) - (p1.getY() * p2.getX());
		
		float x = ((b* ((b*p.getX()) - (a*p.getY()))) - (a*c))/((a*a) + (b*b));
		float y = ((b*-((b*p.getX()) - (a*p.getY()))) - (b*c))/((a*a) + (b*b));
		
		if(x < Math.min(p1.getX(), p2.getX())) return p1.getX() < p2.getX() ? p1 : p2;
		if(x > Math.max(p1.getX(), p2.getX())) return p1.getX() > p2.getX() ? p1 : p2;
		if(y < Math.min(p1.getY(), p2.getY())) return p1.getY() < p2.getY() ? p1 : p2;
		if(y > Math.max(p1.getY(), p2.getY())) return p1.getY() > p2.getY() ? p1 : p2;
		
		return new Point2D(x, y);
	}
	
	@Override
	public void rotate(final Point2D p, final double ang) {
		p1.rotate(p, ang);
		p2.rotate(p, ang);
	}
	@Override
	public String toString() {
		return (int) p1.getX() + " " + (int) p1.getY() + " " + (int) p2.getX() + " " + (int) p2.getY();
	}

	@Override
	public Shape2D add(float x, float y) {
		return null;
	}
}
