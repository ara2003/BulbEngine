package com.greentree.engine.event;

import java.io.Serializable;
import java.util.Set;

public abstract class ListenerManager implements Listener, Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Set<Class<? extends Event>> types;
	
	@SafeVarargs
	public ListenerManager(final Class<? extends Event>... types) {
		this.types = Set.of(types);
	}
	
	public abstract boolean addListener(final Listener listener);
	public abstract void event(Event event);

	public final boolean contains(Event event) {
		return contains(event.getClass());
	}
	
	public boolean contains(Class<? extends Event> event) {
		return types.contains(event);
	}
}
