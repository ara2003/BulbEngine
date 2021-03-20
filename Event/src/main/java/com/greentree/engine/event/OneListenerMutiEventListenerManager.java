package com.greentree.engine.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** @author Arseny Latyshev */
@Deprecated
public abstract class OneListenerMutiEventListenerManager<L extends Listener> extends OneListenerListenerManager<L> {
	
	private static final long serialVersionUID = 1L;
	private final Set<Class<? extends Event>> eventTypes;
	

	@SafeVarargs
	public OneListenerMutiEventListenerManager(final Class<? extends Listener> listenerClass,
			Class<? extends Event>... types) {
		this(listenerClass);
		addEventTypes(types);
	}
	
	public OneListenerMutiEventListenerManager(final Class<? extends Listener> listenerClass) {
		super(listenerClass);
		eventTypes = new HashSet<>();
	}
	
	@SafeVarargs
	private final boolean addEventTypes(final Class<? extends Event>... types) {
		return this.eventTypes.addAll(List.of(types));
	}
	
	@Override
	public final void event(Event event) {
		if(isUse(event)) event0(event);
	}
	
	protected abstract void event0(Event event);
	
	protected boolean isUse(Event event) {
		return eventTypes.contains(event.getClass());
	}
}
