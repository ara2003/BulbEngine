package com.greentree.engine.geom;

import com.greentree.util.Sized;

/**
 * @author Arseny Latyshev
 *
 */
public interface AABB<A extends AABB<A>> {
	
	boolean isIntersect(A other);
}
