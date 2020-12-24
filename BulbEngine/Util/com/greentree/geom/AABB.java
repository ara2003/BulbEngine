package com.greentree.geom;

/**
 * @author Arseny Latyshev
 */
public class AABB {
	
	private float2 min, max;
	
	public AABB(int x, int y, int w, int h) {
		min.x = x;
		min.y = y;
		max.x = w + x;
		max.y = h + y;
		
	}
	public AABB(Shape shape) {
		this(shape, false);
	}
	
	public AABB(Shape shape, boolean fast) {
		min = new float2();
		max = new float2();
		if(fast) {
			Point c = shape.getCenter();
			float r = shape.getRadius();
			min.x = c.getX() - r;
			min.y = c.getY() - r;
			max.x = c.getX() + r;
			max.y = c.getY() + r;
		}else {
			max.x = -Float.MAX_VALUE;
			max.y = -Float.MAX_VALUE;
			min.x = Float.MAX_VALUE;
			min.y = Float.MAX_VALUE;
			for(Point p : shape.getPoints()) {
				max.x = Math.max(max.x, p.getX());
				max.y = Math.max(max.y, p.getY());
				min.x = Math.min(min.x, p.getX());
				min.y = Math.min(min.y, p.getY());
			}
		}
	}
	
	public float getDeltaX() {
		return max.x - min.x;
	}
	
	public float getDeltaY() {
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
	public boolean isTouch(AABB other){
		if((max.x < other.min.x) || (min.x > other.max.x)) return false;
		if((max.y < other.min.y) || (min.y > other.max.y)) return false;
		return true;
	}
}
/**
 * @author Arseny Latyshev
 */
class float2 {
	float x, y;
}
