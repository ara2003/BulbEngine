package com.greentree.engine.geom2d;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.joml.Vector2f;

import com.greentree.engine.geom.Point;

public class Point2D extends Shape2D implements Point<AABB, Point2D, Shape2D> {

	public float x, y;

	public Point2D() {
	}

	public Point2D(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	public Point2D(Point2D point) {
		x = point.x;
		y = point.y;
	}

	public Point2D(Point2D center,  Vector2f vector) {
		x = center.x + vector.x;
		y = center.y + vector.y;
	}

	public Point2D(Point2D center, float vecx, float vecy) {
		x = center.x + vecx;
		y = center.y + vecy;
	}

	public Point2D(Vector2f vec) {
		x = vec.x;
		y = vec.y;
	}

	@Override
	public int getPointsSize() {
		return 1;
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
		if(abs(x - ((Point2D)obj).x) > 1E-9) return false;
		if(abs(y - ((Point2D)obj).y) > 1E-9) return false;
		return true;
	}

	@Override
	public AABB getAABB() {
		return new AABB(x, y, 0, 0);
	}

	@Override
	public Point2D getCenter() {
		return clone();
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
	public boolean isInside(Point2D p) {
		return this.equals(p);
	}

	@Override
	public boolean isIntersect(Shape2D other) {
		return other.isInside(this);
	}
	
	@Override
	public Point2D minPoint(final Point2D p) {
		return clone();
	}

	@Override
	public void rotate(final Point2D c, final double ang) {
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
		return "(" + x + " " + y + ")";
	}

	@Override
	public void transleteX(final Function<Float, Float> t) {
		x = t.apply(x);
	}

	@Override
	public void transleteY(final Function<Float, Float> t) {
		y = t.apply(y);
	}

	public Vector2f getVector(Point2D p) {
		return p.getRadiusVector().sub(getRadiusVector());
	}
}
