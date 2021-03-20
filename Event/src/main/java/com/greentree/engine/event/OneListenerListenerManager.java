package com.greentree.engine.event;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class OneListenerListenerManager<L extends Listener> extends AbstractListenerManager<L> {
	private static final long serialVersionUID = 1L;

	private Class<? extends Listener> listenerClass;
	
	public OneListenerListenerManager(Class<? extends Listener> clazz) {
		listenerClass = clazz;
	}
	
	protected boolean isUse(Listener listener) {
		return listenerClass.isInstance(listener);
	}
	
}
