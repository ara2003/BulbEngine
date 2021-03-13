package com.greentree.engine.event;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class OneListenerListenerManager<L extends Listener> extends ListenerManager {
	private static final long serialVersionUID = 1L;

	protected final Collection<L> listeners;
	private Class<? extends Listener> listenerClass;
	
	public OneListenerListenerManager(Class<? extends Listener> clazz) {
		listenerClass = clazz;
		listeners = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addListener(Listener listener) {
		if(isUse(listener))
			return listeners.add((L) listener);
		return false;
	}
	
	protected boolean isUse(Listener listener) {
		if(listener == null)return false;
		return listenerClass.isInstance(listener);
	}
	
}
