package com.greentree.engine.geom;

/**
 * @author Arseny Latyshev
 *
 */
public interface AABB<A extends AABB<A>> {
	
	boolean isIntersect(A other);
}
