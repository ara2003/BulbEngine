package com.greentree.engine.geom2d;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.joml.Vector2f;

import com.greentree.engine.geom2d.util.Geom2DUtil;

public class Line2D extends Shape2D {

	private final Point2D p1, p2;

	public Line2D(final float f, final float g, final float h, final float i) {
		this(new Point2D(f, g), new Point2D(h, i));
	}

	public Line2D(final Point2D p, final Vector2f v) {
		this.p1 = new Point2D(p);
		this.p2 = new Point2D(p, v);
	}

	public Line2D(final Point2D p1, final Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public boolean isIntersect(final Line2D other) {
		return contact(other) != null;
	}
	
	public Line2D(Vector2f p1, Vector2f p2) {
		this.p1 = new Point2D(p1);
		this.p2 = new Point2D(p2);
	}

	@Override
	protected Line2D clone() {
		return new Line2D(p1.clone(), p2.clone());
	}

	public final Point2D contact(final Line2D other) {
		return Geom2DUtil.contact(this, other, true);
	}
	@Override
	public Shape2D add(Vector2f vec) { 
		p1.add(vec);
		p2.add(vec);
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Line2D)) return false;
		Line2D other = (Line2D) obj;
		if(!Objects.equals(p1, other.p1)) return false;
		if(!Objects.equals(p2, other.p2)) return false;
		return true;
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

	@Override
	public int getPointsSize() {
		return 2;
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
		return Objects.hash(p1, p2);
	}

	@Override
	public boolean isInside(Point2D p) {
		return distanse(p) < 1;
	}

	public float length() {
		final float x = p1.getX() - p2.getX();
		final float y = p1.getY() - p2.getY();
		return (float) Math.sqrt(x * x + y * y);
	}

	@Override
	public Point2D minPoint(final Point2D p) {
//		float a = p2.getX() - p1.getX();
//		float b = p2.getY() - p1.getY();
//		float c = p1.getX() * p2.getY() - p1.getY() * p2.getX();
		
		Point2D p0 = minPoint0(p);
		float x = p0.x;
		float y = p0.y;
		
		
//		float x = (b* (b*p.getX() - a*p.getY()) - a*c)/(a*a + b*b);
//		float y = (b*-(b*p.getX() - a*p.getY()) - b*c)/(a*a + b*b);

		if(p1.getX() > p2.getX()) {
			if(x > p1.getX())return p1;
			if(x < p2.getX())return p2;
		}
		if(p1.getX() < p2.getX()){
			if(x < p1.getX())return p1;
			if(x > p2.getX())return p2;
		}
		if(p1.getY() > p2.getY()) {
			if(y > p1.getY())return p1;
			if(y < p2.getY())return p2;
		}
		if(p1.getY() < p2.getY()){
			if(y < p1.getY())return p1;
			if(y > p2.getY())return p2;
		}
		return p0;
	}

	public Point2D minPoint0(Point2D p) {
		if(p1.equals(p2))return p1;
		Line2D o = clone();
		o.moveTo(p);
		o.rotate(p, Math.PI / 2);
		return Geom2DUtil.contact(o, this, false);
	}

	@Override
	public void rotate(final Point2D p, final double ang) {
		p1.rotate(p, ang);
		p2.rotate(p, ang);
	}
	@Override
	public String toString() {
		return "("+(int) p1.getX() + " " + (int) p1.getY() + " " + (int) p2.getX() + " " + (int) p2.getY()+")";
	}

	@Override
	public Vector2f getNormal(Point2D point) {
		return minPoint0(point).getRadiusVector().sub(point.getRadiusVector());
	}


}
