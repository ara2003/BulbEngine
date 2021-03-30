package com.greentree.event;

/** @author Arseny Latyshev */
public abstract class AbstractListenerManager<E extends Event, L extends Listener> implements ListenerManager {
	
	private static final long serialVersionUID = 1L;
	
	public AbstractListenerManager() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean addListener(final Listener listener) {
		try {
			return addListener0((L) listener);
		}catch(final ClassCastException e) {
		}
		return false;
	}
	protected abstract boolean addListener0(final L listener);
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean event(final Event event) {
		try {
			this.event0((E) event);
		}catch(final ClassCastException e) {
		}
		return false;
	}
	protected abstract void event0(final E event);
}
