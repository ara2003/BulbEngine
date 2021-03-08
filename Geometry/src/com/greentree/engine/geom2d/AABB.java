package com.greentree.engine.geom2d;

/**
 * @author Arseny Latyshev
 */
public class AABB implements com.greentree.engine.geom.AABB<Point2D> {
	
	private final float3 min, max;
	
	public AABB(float x, float y, float w, float h) {
		min = new float3(x, y);
		max = new float3(x + w, y + h);
		
	}
	public AABB(Shape2D shape) {
		this(shape, false);
	}
	
	public AABB(Shape2D shape, boolean fast) {
		min = new float3();
		max = new float3();
		if(fast) {
			Point2D c = shape.getCenter();
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
			for(Point2D p : shape.getPoints()) {
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
	@Override
	public boolean isTouch(com.greentree.engine.geom.AABB<Point2D> other) {
		if((a().x < other.b().x) || (b().x > other.a().x)) return false;
		if((a().y < other.b().y) || (b().y > other.a().y)) return false;
		return true;
	}
	@Override
	public Point2D a() {
		return new Point2D(min.x, min.y);
	}
	@Override
	public Point2D b() {
		return new Point2D(max.x, max.y);
	}
}
/**
 * @author Arseny Latyshev
 */
class float3 {
	
	float x, y;
	
	public float3() {
	}
	
	public float3(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
