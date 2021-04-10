package com.greentree.engine.geom2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joml.Vector2f;

/** @author Arseny Latyshev */
public class ShapeList {
	
	List<Shape2D> list;
	
	public ShapeList() {
		this(10);
	}
	
	public ShapeList(final Collection<Shape2D> list) {
		this.list = new ArrayList<>(list);
	}
	
	public ShapeList(final int n) {
		list = new ArrayList<>(n);
	}
	
	public boolean add(final Shape2D shape) {
		return list.add(shape);
	}
	
	public Point2D rayCast(Point2D start,  Vector2f vector, float maxDistanse){
		return rayCast(start, vector, maxDistanse, 1);
	}
	
	public Point2D rayCast(Point2D start,Vector2f vector, float maxDistanse, double delta){
		if(vector.length() == 0) return null;
		if(delta <= 0) throw new IllegalArgumentException("delta in not pozitive: " + delta);
		vector.mul(1f / vector.length());
		final Point2D p = new Point2D(start.getX(), start.getY());
		float dis = 0;
		int t = 50;
		while((t-- > 0) && (maxDistanse > 0)) {
			dis = GeomUtil2D.distanse(p, list) * 0.99f;
			maxDistanse -= dis;
			p.add(vector.mul(dis));
			if(dis < delta) return p;
		}
		return null;
	}

	public Point2D getCenter() {
		float x = 0,  y = 0;
		for(Shape2D s : list) {
			Point2D c = s.getCenter();
			x += c.x;
			y += c.y;
		}
		return new Point2D(x / list.size(), y / list.size());
	}
	
	
}
