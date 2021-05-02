package com.greentree.engine.core.system.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.greentree.engine.core.object.GameSystem;


/** @author Arseny Latyshev */
public abstract class GroupSystemCollection extends SystemCollection implements Iterable<GameSystemGroup> {
	
	/** id to unnamed group. static to {@link getGroupName} */
	private int k;
	private final Collection<GameSystemGroup> groups;
	
	public GroupSystemCollection() {
		groups = new ArrayList<>();
	}
	
	public GameSystemGroup getOrCreateGroup(String name) {
		
	}
	
	@Override
	public boolean add(final GameSystem system) {
		return false;
	}
	
	@Override
	public <S extends GameSystem> S get(final Class<S> clazz) {
		return ;
	}
	
	protected final String getGroupName(final String name) {
		return name == null ? String.format("default-%d", k) : name;
	}
	
	@Override
	public abstract void initSratr();
	
	@Override
	public final void clear() {
		groups.clear();
	}
	
	
	
	@Override
	public final Iterator<GameSystemGroup> iterator() {
		return groups.iterator();
	}
	
	@Override
	public abstract void update();
	
}
