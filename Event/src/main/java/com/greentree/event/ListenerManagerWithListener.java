package com.greentree.event;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class ListenerManagerWithListener<E extends Event, L extends Listener> extends AbstractListenerManager<E, L> {

	private Collection<L> listeners = new ArrayList<>();
	
	public ListenerManagerWithListener() {
	}
	
	@Override
	protected final boolean addListener0(L listener) {
		if(isUse(listener))return listeners.add(listener);
		return false;
	}
	protected abstract boolean isUse(L listener);

	@Override
	protected final void event0(E event) {
		for(L l : listeners) {
			event(l, event);
		}
	}

	protected abstract void event(L l, E event);
	
}
