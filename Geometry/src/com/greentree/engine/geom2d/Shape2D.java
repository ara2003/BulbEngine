package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.greentree.engine.geom.Shape;
import com.greentree.util.Util;

/**
 * @author Arseny Latyshev
 */
public abstract class Shape2D implements Shape<Point2D, Shape2D> {
	
	protected Point2D center;
	
	public Shape2D() {
	}

	public Shape2D add(@SuppressWarnings("exports") final Vector2f step) {
		return add(new Point2D(step.x, step.y));
	}
	
	public final List<Point2D> contact(final Shape2D other) {
		final List<Line> A = getLines();
		final List<Line> B = other.getLines();
		if(A == null) return new ArrayList<>();
		if(B == null) return new ArrayList<>();
		final List<Point2D> res = new ArrayList<>(A.size() + B.size());
		for(final Line element : A) {
			for(final Line element2 : B) {
				final Point2D c = GeomUtil.contact(element, element2, true);
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
	public float distanse(final Point2D p) {
		return p.distanse(minPoint(p));
	}
	
	public float distanse(Shape2D other) {
		return Util.min(other.getLines(), this::distanse);
	}
	
	@Override
	public AABB getAABB() {
		return new AABB(this);
	}
	
	@Override
	public Point2D getCenter() {
		return center;
	}
	
	public List<Line> getLines() {
		return GeomUtil.toLine(getPoints());
	}
	
	public float getPenetrationDepth(Shape2D other) {
		return -distanse(other);
	}
	
	public float getRadius() {
		float dis = 0;
		for(final Point2D p : getPoints()) {
			dis = Math.max(dis, center.distanse(p));
		}
		return dis;
	}
	
	@Override
	public boolean isTouch(final Shape2D other) {
		return new AABB(this).isTouch(new AABB(other));
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
	
	public void rotate(final Point2D point, final double ang) {
		for(final Point2D p : getPoints()) {
			p.rotate(point, ang);
		}
		if(!getPoints().contains(getCenter())) center.rotate(point, ang);
	}
	
	public void setSize(float width, float height) {
		AABB aabb = new AABB(this);
		transleteX(x->(((x - aabb.getMinX() - (aabb.getDeltaX() / 2)) * width)  / aabb.getDeltaX()) + aabb.getMinX() + (aabb.getDeltaX() / 2));
		transleteX(y->(((y - aabb.getMinY() - (aabb.getDeltaY() / 2)) * height) / aabb.getDeltaY()) + aabb.getMinY() + (aabb.getDeltaY() / 2));
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
	
	protected void trim() {
		try {
			center = GeomUtil.getCenter(getPoints());
		}catch (NullPointerException e) {
		}
	}
	
	@FunctionalInterface
	public interface Translete {
		
		float translete(float c);
	}
	
}
