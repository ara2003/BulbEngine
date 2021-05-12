package com.greentree.engine.core.system.collection;

import com.greentree.engine.core.object.GameSystem;

/** @author Arseny Latyshev */
public abstract class SystemCollection {
	
	public abstract boolean add(final GameSystem system);
	
	public abstract void clear();
	
	public final <S extends GameSystem> boolean containsClass(final Class<S> clazz) {
		return null != get(clazz);
	}
	
	public abstract <S extends GameSystem> S get(final Class<S> clazz);
	
	public abstract Iterable<GameSystem> getSystemIterable();
	
	public abstract void initSratr();
	
	public abstract void update();
	
}
