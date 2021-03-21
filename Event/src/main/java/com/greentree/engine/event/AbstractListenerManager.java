package com.greentree.engine.event;

import java.util.ArrayList;
import java.util.Collection;

/** @author Arseny Latyshev */
public abstract class AbstractListenerManager<L extends Listener> implements ListenerManager {
	
	private static final long serialVersionUID = 1L;
	protected final Collection<L> listeners;
	
	public AbstractListenerManager() {
		this.listeners = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean addListener(Listener listener) {
		if(isUse(listener))
			return this.listeners.add((L) listener);
		return false;
	}
	
	protected abstract boolean isUse(Listener listener);
	
}
