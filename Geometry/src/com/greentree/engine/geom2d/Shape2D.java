package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.greentree.common.math.Mathf;
import com.greentree.engine.geom.Shape;

/**
 * @author Arseny Latyshev
 */
public abstract class Shape2D implements Shape<AABB, Point2D, Shape2D> {
	
	public Shape2D() {
	}

	public Shape2D add(@SuppressWarnings("exports") final Vector2f step) {
		return add(step.x, step.y);
	}
	
	public abstract Shape2D add(float x, float y);

	public final List<Point2D> contact(final Shape2D other) {
		final List<Line> A = getLines();
		final List<Line> B = other.getLines();
		if(A == null) return new ArrayList<>();
		if(B == null) return new ArrayList<>();
		final List<Point2D> res = new ArrayList<>(A.size() + B.size());
		for(final Line element : A) {
			for(final Line element2 : B) {
				final Point2D c = GeomUtil2D.contact(element, element2, true);
				if(c != null) {
					res.add(c);
				}
			}
		}
		return res;
	}
	public float distanse(final Line l) {//TODO
		return Math.min(distanse(l.getP1()), distanse(l.getP2()));
	}
	
	@Override
	public float distanseSqr(final Point2D p) {
		return p.distanseSqr(minPoint(p));
	}
	
	public float distanse(Shape2D other) {
		return Mathf.min(other.getLines(), this::distanse);
	}
	
	@Override
	public AABB getAABB() {
		return new AABB(this);
	}
	
	@Override
	public Point2D getCenter() {
		return GeomUtil2D.getCenter(getPoints());
	}
	
	public List<Line> getLines() {
		return GeomUtil2D.toLine(getPoints());
	}
	
	public float getPenetrationDepth(Shape2D other) {
		return -distanse(other);
	}
	
	public float getRadius() {
		float dis = 0;
		for(final Point2D p : getPoints()) {
			dis = Math.max(dis, getCenter().distanse(p));
		}
		return dis;
	}
	
	@Override
	public boolean isIntersect(final Shape2D other) {
		if(!new AABB(this).isIntersect(new AABB(other)))return false;
		
		return true;
	}
	
	public Point2D minPoint(final Point2D point) {
		Point2D res = null, pi;
		float dis, dis0 = Float.MAX_VALUE;
		for(final Line n : getLines()) {
			pi = n.minPoint(point);
			if(pi == null) {
				continue;
			}
			dis = pi.distanse(point);
			if(dis < dis0) {
				dis0 = dis;
				res = pi;
			}
		}
		return res;
	}
	
	public final void moveTo(final float x, final float y) {
		add(new Vector2f(x - getCenter().getX(), y - getCenter().getY()));
	}
	
	@Override
	public final void moveTo(Point2D p) {
		moveTo(p.getX(), p.getY());
	}

	public abstract void rotate(final Point2D point, final double ang);
	
	public void setSize(float width, float height) {
		AABB aabb = new AABB(this);
		transleteX(x->(((x - aabb.getMinX() - (aabb.getWidth() / 2)) * width)  / aabb.getWidth()) + aabb.getMinX() + (aabb.getWidth() / 2));
		transleteY(y->(((y - aabb.getMinY() - (aabb.getHeight() / 2)) * height) / aabb.getHeight()) + aabb.getMinY() + (aabb.getHeight() / 2));
	}
	
	public final void translete(Translete translete) {
		transleteX(translete);
		transleteY(translete);
	}
	
	public void transleteX(final Translete t) {
		for(final Point2D p : getPoints()) {
			p.transleteX(t);
		}
	}
	
	public void transleteY(final Translete t) {
		for(final Point2D p : getPoints()) {
			p.transleteY(t);
		}
	}
	
	@FunctionalInterface
	public interface Translete {
		
		float translete(float c);
	}
	
}
