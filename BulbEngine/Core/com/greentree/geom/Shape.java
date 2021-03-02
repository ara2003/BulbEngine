package com.greentree.geom;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.greentree.math.MathUtil;

/**
 * @author Arseny Latyshev
 * 
 */
public abstract class Shape {
	
	protected Point center;
	
	public Shape() {
	}
	
	public void add(final Vector2f step) {
		final List<Point> points = getPoints();
		for(final Point point : points) {
			point.add(step);
		}
		if(!points.contains(getCenter())) {
			getCenter().add(step);
		}
	}
	
	public final List<Point> contact(final Shape other) {
		final List<Line> A = getLines();
		final List<Line> B = other.getLines();
		if(A == null) return new ArrayList<>();
		if(B == null) return new ArrayList<>();
		final List<Point> res = new ArrayList<>(A.size() + B.size());
		for(final Line element : A) {
			for(final Line element2 : B) {
				final Point c = GeomUtil.contact(element, element2, true);
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
	public float distanse(final Point p) {
		return p.distanse(minPoint(p));
	}
	
	public float distanse(Shape other) {
		return MathUtil.minElement(other.getLines(), this::distanse).distanse(this);
	}
	
	public Point getCenter() {
		return center;
	}
	
	public List<Line> getLines() {
		return GeomUtil.toLine(getPoints());
	}
	
	public float getPenetrationDepth(Shape other) {
		return -distanse(other);
	}
	
	public abstract List<Point> getPoints();
	
	public float getRadius() {
		float dis = 0;
		for(final Point p : getPoints()) {
			dis = Math.max(dis, center.distanse(p));
		}
		return dis;
	}
	
	public boolean isTouch(final Shape other) {
		return new AABB(this).isTouch(new AABB(other));
	}
	
	public Point minPoint(final Point point) {
		Point res = null, pi;
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
	
	public final void moveTo(Point p) {
		moveTo(p.getX(), p.getY());
	}
	
	public void rotate(final Point point, final double ang) {
		for(final Point p : getPoints()) {
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
		for(final Point p : getPoints()) {
			p.transleteX(t);
		}
	}
	
	public void transleteY(final Translete t) {
		for(final Point p : getPoints()) {
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
