package com.greentree.event;

public interface ListenerManager {
	
	public abstract boolean addListener(final Listener listener);
	public abstract boolean event(final Event event);
	
}
