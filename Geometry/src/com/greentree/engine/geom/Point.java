package com.greentree.engine.geom;


/**
 * @author Arseny Latyshev
 *
 */
public interface Point<P extends Point<P, S>, S extends Shape<P, S>> extends Shape<P, S> {

	public float distanse(P p);
	
}
