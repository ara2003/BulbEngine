package com.greentree.engine.geom2d;

import com.greentree.common.Sized;

/** @author Arseny Latyshev */
public class AABB implements com.greentree.engine.geom.AABB<AABB>, Sized {
	
	private final float3 min, max;
	
	public AABB(float x, float y, float w, float h) {
		min = new float3(x, y);
		max = new float3(x + w, y + h);
	}
	
	@Override
	public String toString() {
		return "AABB [min=" + min + ", max=" + max + "]";
	}
	
	public AABB(Shape2D shape) {
		min = new float3();
		max = new float3();
		max.x = -Float.MAX_VALUE;
		max.y = -Float.MAX_VALUE;
		min.x = Float.MAX_VALUE;
		min.y = Float.MAX_VALUE;
		for(Point2D p : shape.getPoints()) {
			max.x = Math.max(max.x, p.getX());
			max.y = Math.max(max.y, p.getY());
			min.x = Math.min(min.x, p.getX());
			min.y = Math.min(min.y, p.getY());
		}
	}
	
	public float getWidth() {
		return max.x - min.x;
	}
	
	public float getHeight() {
		return max.y - min.y;
	}
	
	public float getMaxX() {
		return max.x;
	}
	
	public float getMaxY() {
		return max.y;
	}
	
	public float getMinX() {
		return min.x;
	}
	
	public float getMinY() {
		return min.y;
	}
	
	@Override
	public boolean isIntersect(AABB other) {
		if(max.x < other.min.x || min.x > other.max.x) return false;
		if(max.y < other.min.y || min.y > other.max.y) return false;
		return true;
	}
}

/** @author Arseny Latyshev */
class float3 {
	
	float x, y;
	
	@Override
	public String toString() {
		return "[x " + x + " y " + y + "]";
	}

	public float3() {
	}
	
	public float3(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
