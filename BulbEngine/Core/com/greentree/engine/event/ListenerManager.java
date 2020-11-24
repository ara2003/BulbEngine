package com.greentree.engine.event;

import java.io.Serializable;

public abstract class ListenerManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public abstract void addListener(final Listener listener);
	public abstract void event(Event event);
}
