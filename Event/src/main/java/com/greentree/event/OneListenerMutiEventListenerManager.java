package com.greentree.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** @author Arseny Latyshev */
@Deprecated
public abstract class OneListenerMutiEventListenerManager<E extends Event, L extends Listener> extends OneListenerListenerManager<E, L> {
	
	private static final long serialVersionUID = 1L;
	private final Set<Class<? extends Event>> eventTypes;
	

	@SafeVarargs
	public OneListenerMutiEventListenerManager(final Class<L> listenerClass,
			Class<? extends E>... types) {
		this(listenerClass);
		addEventTypes(types);
	}
	
	public OneListenerMutiEventListenerManager(final Class<L> listenerClass) {
		super(listenerClass);
		eventTypes = new HashSet<>();
	}
	
	@SafeVarargs
	private final boolean addEventTypes(final Class<? extends E>... types) {
		return this.eventTypes.addAll(List.of(types));
	}
	
	@Override
	public final void event(L l, E event) {
		if(isUse(event)) event0(l, event);
	}
	
	protected abstract void event0(L l, E event);
	
	protected final boolean isUse(E event) {
		return eventTypes.contains(event.getClass());
	}
}
