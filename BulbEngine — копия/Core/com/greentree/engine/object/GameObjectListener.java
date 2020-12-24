package com.greentree.engine.object;

import com.greentree.engine.event.Listener;

public abstract class GameObjectListener implements Listener {
	
	private static final long serialVersionUID = 1L;
	
	public void create(final GameObject gameObject) {
	}
	
	public void destroy(final GameObject gameObject) {
	}
}
