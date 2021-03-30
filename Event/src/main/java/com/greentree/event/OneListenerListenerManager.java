package com.greentree.event;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class OneListenerListenerManager<E extends Event, L extends Listener> extends ListenerManagerWithListener<E, L> {
	private static final long serialVersionUID = 1L;

	private Class<L> listenerClass;
	
	public OneListenerListenerManager(Class<L> clazz) {
		listenerClass = clazz;
	}
	
	@Override
	protected boolean isUse(Listener listener) {
		return listenerClass.isInstance(listener);
	}
	
}
