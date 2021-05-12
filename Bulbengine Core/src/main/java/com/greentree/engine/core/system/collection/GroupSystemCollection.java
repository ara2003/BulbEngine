package com.greentree.engine.core.system.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greentree.common.ClassUtil;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.system.GroupSystem;


/** @author Arseny Latyshev */
public abstract class GroupSystemCollection extends SystemCollection implements Iterable<GameSystemGroup> {
	
	/** id to unnamed group. static to {@link getGroupName} */
	private int k;
	private final Collection<GameSystemGroup> groups;
	
	public GroupSystemCollection() {
		groups = new CopyOnWriteArrayList<>();
	}
	
	@Override
	public final String toString() {
		return groups.toString();
	}
	
	@Override
	public boolean add(final GameSystem system) {
		final Collection<GroupSystem> list = ClassUtil.getAllAnnotations(system.getClass(), GroupSystem.class);
		if(list.size() > 1) throw new IllegalArgumentException("class has more then 1 annotation " + GroupSystem.class.getSimpleName());
		String name = null;
		for(final var i : list) if(name == null) name = i.value();
		return getOrCreateGroup(getGroupName(name)).add(system);
	}
	
	@Override
	public final void clear() {
		groups.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S extends GameSystem> S get(final Class<S> clazz) {
		return (S) groups.parallelStream().map(e->e.get(clazz)).filter(e->e != null).findAny().orElse(null);
	}
	
	protected final String getGroupName(final String name) {
		return name == null ? String.format("default-%d", k++) : name;
	}
	
	public final GameSystemGroup getOrCreateGroup(final String name) {
		var g = groups.parallelStream().filter(e->e.getName().equals(name)).findAny().orElse(null);
		if(g == null) {
			g = new GameSystemGroup(getGroupName(name));
			groups.add(g);
		}
		return g;
	}
	
	@Override
	public final Iterable<GameSystem> getSystemIterable() {
		return ()->new Iterator<>() {
			
			final Iterator<GameSystemGroup> giterator = groups.iterator();
			Iterator<GameSystem> iterator = giterator.next().iterator();
			
			@Override
			public boolean hasNext() {
				return giterator.hasNext();
			}
			
			@Override
			public GameSystem next() {
				if(giterator.hasNext()) {
					if(iterator.hasNext()) return iterator.next();
					else {
						iterator = giterator.next().iterator();
						return iterator.next();
					}
				}else
					throw new NoSuchElementException();
			}
		};
	}
	
	@Override
	public abstract void initSratr();
	
	@Override
	public final Iterator<GameSystemGroup> iterator() {
		return groups.iterator();
	}
	
	@Override
	public abstract void update();
	
}
