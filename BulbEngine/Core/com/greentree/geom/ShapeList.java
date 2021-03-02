package com.greentree.geom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joml.Vector2f;

/** @author Arseny Latyshev */
public class ShapeList {
	
	List<Shape> list;
	
	public ShapeList() {
		this(10);
	}
	
	public ShapeList(final Collection<Shape> list) {
		this.list = new ArrayList<>(list);
	}
	
	public ShapeList(final int n) {
		list = new ArrayList<>(n);
	}
	
	public boolean add(final Shape shape) {
		return list.add(shape);
	}
	
	/**
	 * @return
	 */
	public Point getCenter() {
		return GeomUtil.getCenter(list);
	}
	public Point rayCast(Point start, Vector2f vector, float maxDistanse){
		return rayCast(start, vector, maxDistanse, 1);
	}
	
	public Point rayCast(Point start, Vector2f vector, float maxDistanse, double delta){
		if(vector.length() == 0) return null;
		if(delta <= 0) throw new IllegalArgumentException("delta in not pozitive: " + delta);
		
		vector.mul(1f / vector.length());
		final Point p = new Point(start.getX(), start.getY());
		float dis = 0;
		int t = 50;
		while((t-- > 0) && (maxDistanse > 0)) {
			dis = GeomUtil.distanse(p, list) * 0.99f;
			maxDistanse -= dis;
			p.add(vector.mul(dis));
			if(dis < delta) return p;
		}
		return null;
	}
	
	
}
