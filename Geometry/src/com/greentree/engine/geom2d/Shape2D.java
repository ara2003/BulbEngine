package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.joml.Vector2f;

import com.greentree.common.math.Mathf;
import com.greentree.engine.geom.Shape;
import com.greentree.engine.geom2d.util.Geom2DUtil;

/** @author Arseny Latyshev */
public abstract class Shape2D implements Shape<AABB, Point2D, Shape2D> {

	public Shape2D() {
	}

	public Shape2D add(float x, float y) {
		return add(new Vector2f(x, y));
	}

	public Shape2D add(final Vector2f step) {
		return this.add(step.x, step.y);
	}

	public final List<Point2D> contact(final Shape2D other) {
		if(this instanceof Line2D && other instanceof Line2D) {
			final List<Point2D> res = new ArrayList<>(1);
			res.add(((Line2D)other).contact((Line2D)this));
			return res;
		}
		final List<Line2D> A = getLines(true);
		final List<Line2D> B = other.getLines(true);
		if(A == null) return new ArrayList<>();
		if(B == null) return new ArrayList<>();
		final List<Point2D> res = new ArrayList<>();
		for(final Line2D a : A) for(final Line2D b : B) {
			final Point2D c = a.contact(b);
			if(c == null)continue;
			res.add(c);
		}
		return res;
	}


	@Override
	public float distanse(Point2D p) {
		return (float) Math.sqrt(distanseSqr(p));
	}

	public final float distanse(final Shape2D other) {
		return Mathf.sqrt(distanseSqr(other));
	}

	@Override
	public float distanseSqr(final Point2D p) {
		return p.distanseSqr(minPoint(p));
	}

	public final float distanseSqr(final Shape2D other) {
		return distanseSqr(minPoint(Mathf.minElement(other.getPoints(), p->distanseSqr(minPoint(p)))));
	}

	@Override
	public AABB getAABB() {
		return new AABB(this);
	}

	@Override
	public Point2D getCenter() {
		return Geom2DUtil.getCenter(getPoints());
	}

	public final List<Line2D> getLines() {
		return getLines(true);
	}
	public final List<Line2D> getLines(boolean stripe) {
		return Geom2DUtil.toLine(getPoints(), stripe);
	}

	public final float getPenetrationDepth(final Shape2D other) {
		List<Point2D> p = other.contact(this);
		if(p.size() < 2)return 0;
		Point2D p1 = p.get(0);
		Point2D p2 = p.get(1);
		
		return -this.distanse(other);
	}

	public int getPointsSize() {
		return getPoints().size();
	}

	@Override
	public float getRadius() {
		float dis = 0;
		for(final Point2D p : getPoints()) dis = Math.max(dis, getCenter().distanse(p));
		return dis;
	}

	@Override
	public boolean isInside(Point2D p) {
		List<Point2D> points = getPoints();
		{
			var p0 = points.get(0);
			points.add(p0);
		}
		for(int i = 0; i < points.size() - 1; i++)
			if(0 > Geom2DUtil.areaTriangle(points.get(i), points.get(i+1), p))return false;
		return true;
	}

	@Override
	public boolean isIntersect(final Shape2D other) {
		return Geom2DUtil.isIntersect(this, other);
	}

	public Point2D minPoint(final Point2D point) {
		Point2D res = null, pi;
		float   dis, dis0 = Float.MAX_VALUE;
		for(final Line2D n : getLines(true)) {
			pi = n.minPoint(point);
			if(pi == null) continue;
			dis = pi.distanseSqr(point);
			if(dis < dis0) {
				dis0 = dis;
				res  = pi;
			}
		}
		return res;
	}
	
	public Point2D minPoint(final Shape2D shape) {
		return Mathf.minElement(shape.getPoints(), p->distanseSqr(minPoint(p)));
	}
	

	public final Shape2D moveTo(final float x, final float y) {
		final Point2D p = getCenter();
		return this.add(new Vector2f(x - p.getX(), y - p.getY()));
	}

	@Override
	public final Shape2D moveTo(final Point2D p) {
		this.moveTo(p.getX(), p.getY());
		return this;
	}

	public abstract void rotate(final Point2D point, final double ang);

	public void setSize(final float width, final float height) {
		final AABB aabb = new AABB(this);
		transleteX(
				x->(x - aabb.getMinX() - aabb.getWidth() / 2) * width / aabb.getWidth() + aabb.getMinX() + aabb.getWidth() / 2);
		transleteY(y->(y - aabb.getMinY() - aabb.getHeight() / 2) * height / aabb.getHeight() + aabb.getMinY()
		+ aabb.getHeight() / 2);
	}

	public final void translete(final Function<Float, Float> translete) {
		transleteX(translete);
		transleteY(translete);
	}

	public void transleteX(final Function<Float, Float> t) {
		for(final Point2D p : getPoints()) p.transleteX(t);
	}

	public void transleteY(final Function<Float, Float> t) {
		for(final Point2D p : getPoints()) p.transleteY(t);
	}

	public Vector2f getNormal(Point2D point) {
		return minLine0(point).getNormal(point);
	}

	private Line2D minLine0(Point2D point) {
		return Mathf.minElement(getLines(), l -> l.distanseSqr(this));
	}
	
}
