package com.greentree.engine.component.util;

import com.greentree.engine.event.Listener;

public abstract class GameComponentListener implements Listener {
	
	private static final long serialVersionUID = 1L;
	
	public void create(final GameComponent gameObject) {
	}
	
	public void destroy(final GameComponent gameObject) {
	}
}
