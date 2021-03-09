package com.greentree.engine.geom3d;

/**
 * @author Arseny Latyshev
 */
public class AABB implements com.greentree.engine.geom.AABB<AABB> {
	
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
	public boolean isIntersect(AABB other) {
		if(max.x < other.min.x || min.x > other.max.x) return false;
		if(max.y < other.min.y || min.y > other.max.y) return false;
		if(max.z < other.min.z || min.z > other.max.z) return false;
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
