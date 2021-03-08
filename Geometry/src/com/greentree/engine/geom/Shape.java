package com.greentree.engine.geom;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Arseny Latyshev
 */
public interface Shape<P extends Point<P, S>, S extends Shape<P, S>> {
	
	@SuppressWarnings("unchecked")
	default S add(P step) {
		final List<P> points = getPoints();
		for(final P point : points) {
			point.add(step);
		}
		if(!points.contains(getCenter())) {
			getCenter().add(step);
		}
		return (S) this;
	}
	default S add(S s) {
		return add(s.getCenter());
	}
	
	float distanse(P p);
	
	default void forEach(Consumer<P> consumer) {
		for(P a : getPoints())consumer.accept(a);
	}
	
	AABB<P> getAABB();
	P getCenter();
	List<P> getPoints();
	float getRadius();
	boolean isTouch(S other);
	
	default void moveTo(P p) {
		sub(getCenter());
		add(p);
	}
	@SuppressWarnings("unchecked")
	default S mul(float f) {
		final List<P> points = getPoints();
		for(final P point : points) {
			point.mul(f);
		}
		if(!points.contains(getCenter())) {
			getCenter().mul(f);
		}
		return (S) this;
	}
	default S sub(P p){
		return add(p.mul(-1));
	}
}
