package com.greentree.engine.core.system.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.greentree.common.Updating;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.system.GroupSystem;

/** @author Arseny Latyshev */
public class GameSystemGroup extends ArrayList<GameSystem> implements Updating {
	
	private static final long serialVersionUID = 1L;
	private final String name;
	
	public GameSystemGroup(final String name) {
		this.name = name;
	}
	
	@Override
	public boolean add(final GameSystem e) {
		if(e == null) throw new UnsupportedOperationException("GameSystemGroup cannot add null");
		final boolean a = super.add(e);
		trim();
		return a;
	}
	
	@Override
	public boolean contains(final Object o) {
		if(o instanceof GameSystem) return containsClass(o.getClass());
		return false;
	}
	
	public boolean containsClass(final Class<?> cl) {
		return null != get(cl);
	}
	
	public GameSystem get(final Class<?> clazz) {
		return parallelStream().filter(e->e.getClass().equals(clazz)).findAny().orElse(null);
	}
	
	public String getName() {
		return name;
	}
	
	private byte getPrioryty(final GameSystem s) {
		try {
			return s.getClass().getAnnotation(GroupSystem.class).priority();
		}catch(final NullPointerException e) {
		}catch(final Exception e) {
			Log.warn(e);
		}
		return 0;
	}
	
	public void initSratr() {
		for(final GameSystem system : this) system.initSratr();
	}
	
	public void trim() {
		Collections.sort(this, Comparator.comparing(this::getPrioryty));
	}
	
	@Override
	public void update() {
		for(var i : this)i.update();
	}
	
	
	
}
