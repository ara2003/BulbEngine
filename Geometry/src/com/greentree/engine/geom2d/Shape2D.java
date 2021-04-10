package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.greentree.common.math.Mathf;
import com.greentree.engine.geom.Shape;

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
		final List<Line> A = this.getLines();
		final List<Line> B = other.getLines();
		if(A == null) return new ArrayList<>();
		if(B == null) return new ArrayList<>();
		final List<Point2D> res = new ArrayList<>(A.size() + B.size());
		for(final Line element : A) for(final Line element2 : B) {
			final Point2D c = GeomUtil2D.contact(element, element2, true);
			if(c != null) res.add(c);
		}
		return res;
	}
	
	public float distanse(final Line l) {//TODO
		return Math.min(this.distanse(l.getP1()), this.distanse(l.getP2()));
	}
	
	public float distanse(final Shape2D other) {
		return Mathf.min(other.getLines(), this::distanse);
	}
	
	@Override
	public float distanseSqr(final Point2D p) {
		return p.distanseSqr(this.minPoint(p));
	}
	
	@Override
	public AABB getAABB() {
		return new AABB(this);
	}
	
	@Override
	public Point2D getCenter() {
		return GeomUtil2D.getCenter(this.getPoints());
	}
	
	public List<Line> getLines() {
		return GeomUtil2D.toLine(this.getPoints());
	}
	
	public float getPenetrationDepth(final Shape2D other) {
		return -this.distanse(other);
	}
	
	@Override
	public float getRadius() {
		float dis = 0;
		for(final Point2D p : this.getPoints()) dis = Math.max(dis, this.getCenter().distanse(p));
		return dis;
	}
	
	@Override
	public boolean isIntersect(final Shape2D other) {
		if(!new AABB(this).isIntersect(new AABB(other))) return false;
		return true;
	}
	
	public Point2D minPoint(final Point2D point) {
		Point2D res = null, pi;
		float   dis, dis0 = Float.MAX_VALUE;
		for(final Line n : this.getLines()) {
			pi = n.minPoint(point);
			if(pi == null) continue;
			dis = pi.distanse(point);
			if(dis < dis0) {
				dis0 = dis;
				res  = pi;
			}
		}
		return res;
	}
	
	public final void moveTo(final float x, final float y) {
		final Point2D p = this.getCenter();
		this.add(new Vector2f(x - p.getX(), y - p.getY()));
	}
	
	@Override
	public final void moveTo(final Point2D p) {
		this.moveTo(p.getX(), p.getY());
	}
	
	public abstract void rotate(final Point2D point, final double ang);
	
	public void setSize(final float width, final float height) {
		final AABB aabb = new AABB(this);
		this.transleteX(
				x->(x - aabb.getMinX() - aabb.getWidth() / 2) * width / aabb.getWidth() + aabb.getMinX() + aabb.getWidth() / 2);
		this.transleteY(y->(y - aabb.getMinY() - aabb.getHeight() / 2) * height / aabb.getHeight() + aabb.getMinY()
				+ aabb.getHeight() / 2);
	}
	
	public final void translete(final Translete translete) {
		this.transleteX(translete);
		this.transleteY(translete);
	}
	
	public void transleteX(final Translete t) {
		for(final Point2D p : this.getPoints()) p.transleteX(t);
	}
	
	public void transleteY(final Translete t) {
		for(final Point2D p : this.getPoints()) p.transleteY(t);
	}
	
	@FunctionalInterface
	public interface Translete {
		
		float translete(float c);
	}
}
