package com.greentree.engine.core.system.collection;

import java.util.Collection;
import java.util.Iterator;

import com.greentree.common.collection.OneClassSet;
import com.greentree.engine.core.object.GameSystem;


/** @author Arseny Latyshev */
public class SimpleSystemCollection extends SystemCollection implements Iterable<GameSystem> {
	
	private final Collection<GameSystem> systems;
	
	public SimpleSystemCollection() {
		systems = new OneClassSet<>();
	}
	
	@Override
	public boolean add(final GameSystem system) {
		return systems.add(system);
	}
	
	@Override
	public void clear() {
		systems.clear();
	}
	
	@Override
	public <S extends GameSystem> S get(final Class<S> clazz) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<GameSystem> getSystemIterable() {
		return (Iterable<GameSystem>) systems.iterator();
	}
	
	@Override
	public void initSratr() {
		for(final var i : this) i.initSratr();
	}
	
	@Override
	public Iterator<GameSystem> iterator() {
		return systems.iterator();
	}
	
	@Override
	public void update() {
		for(final var i : this) i.update();
	}
	
}
