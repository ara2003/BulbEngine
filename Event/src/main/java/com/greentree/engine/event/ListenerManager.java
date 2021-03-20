package com.greentree.engine.event;

import java.io.Serializable;

public interface ListenerManager extends Listener, Serializable {
	
	public abstract boolean addListener(final Listener listener);
	public abstract void event(Event event);
	
}
