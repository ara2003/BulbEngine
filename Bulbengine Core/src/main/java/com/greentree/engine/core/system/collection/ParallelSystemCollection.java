package com.greentree.engine.core.system.collection;

import com.greentree.engine.core.object.GameSystem;

/** @author Arseny Latyshev */
public class ParallelSystemCollection extends GroupSystemCollection {
	private static final long serialVersionUID = 1L;

	
	@Override
	public void update() {
	}

	@Override
	public void initSratr() {
	}

	@Override
	public boolean add(GameSystem system) {
		return false;
	}

	@Override
	public <S extends GameSystem> S get(Class<S> clazz) {
		return null;
	}

	
}