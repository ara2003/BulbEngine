package com.greentree.engine.geom2d;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;

import com.greentree.engine.geom.Point;

public class Point2D extends Shape2D implements Point<AABB, Point2D, Shape2D> {
	
	@Override
	public AABB getAABB() {
		return new AABB(x, y, 0, 0);
	}
	
	public float x, y;
	
	public Point2D() {
	}
	
	public Point2D(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D(Point2D center,  Vector2f vector) {
		x = center.x + vector.x;
		y = center.y + vector.y;
	}
	
	@Override
	public Shape2D add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	@Override
	public Point2D clone() {
		return new Point2D(x, y);
	}
	
	@Override
	public float distanseSqr(final Point2D p) {
		float dx = p.getX() - x;
		float dy = p.getY() - y;
		return dx*dx + dy*dy;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Point2D)) return false;
		final Point2D other = (Point2D) obj;
		if(abs(x - other.x) > 1E-9) return false;
		if(abs(y - other.y) > 1E-9) return false;
		return true;
	}
	
	@Override
	public Point2D getCenter() {
		return clone();
	}
	
	@Override
	public List<Line> getLines() {
		return new ArrayList<>();
	}
	
	@Override
	public List<Point2D> getPoints() {
		return Arrays.asList(clone());
	}
	
	@Override
	public float getRadius() {
		return 0;
	}
	
	
	public Vector2f getRadiusVector() {
		return new Vector2f(x, y);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	@Override
	public Point2D minPoint(final Point2D p) {
		return clone();
	}
	
	@Override
	public void rotate(final Point2D c, final double ang) {
		if(equals(c)) return;
		x -= c.getX();
		y -= c.getY();
		final float x_ = x, y_ = y;
		x = (float) (x_ * cos(ang) + y_ * -sin(ang));
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
