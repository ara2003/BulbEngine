package com.greentree.engine.object;

import com.greentree.engine.event.Listener;


public abstract class MessageListener implements Listener {
	private static final long serialVersionUID = 1L;

	public abstract void massage(String text);
	
}
