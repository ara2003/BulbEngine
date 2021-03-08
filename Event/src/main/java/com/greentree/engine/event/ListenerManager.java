package com.greentree.engine.event;

import java.io.Serializable;
import java.util.Set;

public abstract class ListenerManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Set<Class<? extends Event>> types;
	
	@SuppressWarnings("unchecked")
	public ListenerManager(final Class<? extends Event>... types) {
		this.types = Set.of(types);
	}
	
	public abstract void addListener(final Listener listener);
	public abstract void event(Event event);
	
	public Set<Class<? extends Event>> getEventTypes() {
		return types;
	}
}
