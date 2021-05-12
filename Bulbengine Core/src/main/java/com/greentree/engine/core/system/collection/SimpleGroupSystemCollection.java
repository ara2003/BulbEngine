package com.greentree.engine.core.system.collection;


/** @author Arseny Latyshev */
public class SimpleGroupSystemCollection extends GroupSystemCollection {
	
	public SimpleGroupSystemCollection() {
	}
	
	@Override
	public void initSratr() {
		for(final var i : this) i.initSratr();
	}
	
	@Override
	public void update() {
		for(final var i : this) i.update();
	}
	
}
