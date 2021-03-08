package com.greentree.engine.geom;

/**
 * @author Arseny Latyshev
 *
 */
public interface AABB<P extends Point<P, ?>> {
	
	P a();
	P b();
	boolean isTouch(AABB<P> other);
}
