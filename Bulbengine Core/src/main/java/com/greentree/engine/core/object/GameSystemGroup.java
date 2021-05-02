package com.greentree.engine.core.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.greentree.common.Updating;
import com.greentree.common.concurent.MultyTask;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.system.GroupSystem;

/** @author Arseny Latyshev */
public class GameSystemGroup extends ArrayList<GameSystem> implements Updating {
	
	private static final long serialVersionUID = 1L;
	private final String name;
	private final boolean mainthread;
	private boolean updated = true;
	private final Runnable runnable;
	
	protected GameSystemGroup(final String name, final boolean mainthread) {
		this.name       = name;
		this.mainthread = mainthread;
		runnable        = ()-> {
							for(final GameSystem system : GameSystemGroup.this) system.update();
							updated = true;
						};
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
	
	public boolean isUpdated() {
		return updated;
	}
	
	@Override
	public String toString() {
		return String.format("%s%s:%s", name, mainthread ? "(main)" : "", super.toString());
	}
	
	public void trim() {
		Collections.sort(this, Comparator.comparing(this::getPrioryty));
	}
	
	@Override
	public void update() {
		if(!isUpdated()) throw new UnsupportedOperationException("previous update not completed " + name);
		updated = false;
		if(mainthread)
			runnable.run();
		else
			MultyTask.task(runnable);
	}
	
	
	
}
