package com.greentree.geom;

import java.util.ArrayList;
import java.util.List;

import com.greentree.geom.Shape.T;
import com.greentree.util.math.vector.float2f;

public abstract class Shape {
	
	public abstract void add(float2f step);

	public final List<Point> contact(final Shape other) {
		final List<Line> A = getLines();
		final List<Line> B = other.getLines();
		if(A == null) return new ArrayList<>();
		if(B == null) return new ArrayList<>();
		final List<Point> res = new ArrayList<>(A.size() + B.size());
		for(final Line element : A) for(final Line element2 : B) {
			final Point c = GeomUtil.contact(element, element2, true);
			if(c != null) res.add(c);
		}
		return res;
	}

	public boolean isClosed(){
		return true;
	}

	public void moveTo(final float x, final float y) {
		add(new float2f(x - getCenter().getX(), y - getCenter().getY()));
	}
	
	@FunctionalInterface
	public interface T {

		public Point translete(float x, float y);
	}
	public List<Point> contactLine(final Line line) {
		final List<Line> A = getLines();
		if(A == null) return new ArrayList<>();
		final List<Point> res = new ArrayList<>(2);
		for(final Line element : A) {
			final Point c = GeomUtil.contactLine(element, line);
			if(c != null) res.add(c);
		}
		return res;
	}
	
	public float distanse(final Point p) {
		return p.distanse(minPoint(p));
	}
	
	public Point getCenter() {
		return GeomUtil.getCenter(getPoints());
	}

	public List<Line> getLines() {
		return GeomUtil.toLine(getPoints());
	}
	
	public abstract List<Point> getPoints();
	
	public float getRadius() {
		final Point c = getCenter();
		float dis = 0;
		for(final Point p : getPoints()) dis = Math.max(dis, c.distanse(p));
		return dis;
	}

	public Point minPoint(final Point point) {
		Point res = null, pi;
		float dis, dis0 = Float.MAX_VALUE;
		for(final Line n : getLines()) {
			pi = n.minPoint(point);
			if(pi == null) continue;
			dis = pi.distanse(point);
			if(dis < dis0) {
				dis0 = dis;
				res = pi;
			}
		}
		return res;
	}

	public abstract void rotate(Point point, double ang);
}
