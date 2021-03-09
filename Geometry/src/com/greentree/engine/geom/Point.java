package com.greentree.engine.geom;

/**
 * @author Arseny Latyshev
 *
 */
public interface Point<A extends AABB<A>, P extends Point<A, P, S>, S extends Shape<A, P, S>> extends Shape<A, P, S> {
	
}
