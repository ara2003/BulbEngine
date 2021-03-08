package com.greentree.engine.geom3d;

/**
 * @author Arseny Latyshev
 */
public class AABB implements com.greentree.engine.geom.AABB<Point3D> {
	
	private final float3 min, max;
	
	public AABB(Shape3D shape) {
		min = new float3();
		max = new float3();
		max.x = -Float.MAX_VALUE;
		max.y = -Float.MAX_VALUE;
		max.z = -Float.MAX_VALUE;
		min.x = Float.MAX_VALUE;
		min.y = Float.MAX_VALUE;
		min.z = Float.MAX_VALUE;
		shape.forEach(p->{
			max.x = Math.max(max.x, p.x);
			max.y = Math.max(max.y, p.y);
			max.z = Math.max(max.z, p.z);
			min.x = Math.min(min.x, p.x);
			min.y = Math.min(min.y, p.y);
			min.z = Math.min(min.z, p.z);
		});
	}
	
	@Override
	public Point3D a() {
		return new Point3D(max.x, max.y, max.z);
	}
	
	@Override
	public Point3D b() {
		return new Point3D(min.x, min.y, min.z);
	}
	
	@Override
	public boolean isTouch(com.greentree.engine.geom.AABB<Point3D> other) {
		if((a().x < other.b().x) || (b().x > other.a().x)) return false;
		if((a().y < other.b().y) || (b().y > other.a().y)) return false;
		if((a().z < other.b().z) || (b().z > other.a().z)) return false;
		return true;
	}
	
}
/**
 * @author Arseny Latyshev
 */
class float3 {
	
	float x, y, z;
	
	public float3() {
	}
	
	public float3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
