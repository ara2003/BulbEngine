package com.greentree.action;

import com.greentree.event.AbstractListenerManager;
import com.greentree.event.Event;
import com.greentree.event.Listener;

/** @author Arseny Latyshev */
public abstract class ActionListenerManager<E extends Event, L extends Listener> extends AbstractListenerManager<E, L> {
	
	private static final long serialVersionUID = 1L;
	protected AbstractEventAction<E, L> action;
	
	public ActionListenerManager(final AbstractEventAction<E, L> action) {
		this.action = action;
	}
	
	@Override
	public boolean addListener0(final L listener) {
		return this.action.addListener(listener);
	}
	
	@Override
	public void event0(final E event) {
		this.action.action(event);
	}
}
