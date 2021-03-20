package com.greentree.engine.event;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class MutiListenerListenerManager extends AbstractListenerManager<Listener> {

	private static final long serialVersionUID = 1L;

	private Collection<Class<? extends Listener>> listenerClasses;
	
	@SafeVarargs
	public MutiListenerListenerManager(Class<? extends Listener>...clazz) {
		listenerClasses = Arrays.asList(clazz);
	}
	
	protected boolean isUse(Listener listener) {
		return listenerClasses.parallelStream().anyMatch(e -> e.isInstance(listener));
	}
	
}
