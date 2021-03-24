package com.greentree.engine.geom;

import java.util.List;
import java.util.function.Consumer;

/** @author Arseny Latyshev */
public interface Shape<A extends AABB<A>, P extends Point<A, P, S>, S extends Shape<A, P, S>> {
	
	float distanseSqr(P p);
	
	default float distanse(P p) {
		return (float) Math.sqrt(distanseSqr(p));
	}
	
	default void forEach(Consumer<P> consumer) {
		for(P a : getPoints()) consumer.accept(a);
	}
	
	A getAABB();
	P getCenter();
	List<P> getPoints();
	float getRadius();
	boolean isIntersect(S other);
	void moveTo(P p); 
}
