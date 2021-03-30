package com.greentree.event;

import java.io.Serializable;

public interface ListenerManager extends Serializable {
	
	public abstract boolean addListener(final Listener listener);
	public abstract boolean event(final Event event);
	
}
