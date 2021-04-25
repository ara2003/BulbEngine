package com.greentree.graphics.input.event;

import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
public final class KeyRepleasedEvent implements KeyEvent {
	
	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyRepleasedEvent(final int key) {
		this.key = key;
	}
	
	public static KeyRepleasedEvent getInstanse(final EventSystem eventSystem, final int key) {
		KeyRepleasedEvent event = eventSystem.get(KeyRepleasedEvent.class);
		if(event == null) event = new KeyRepleasedEvent(key);
		else event.key = key;
		return event;
	}
	
	public int getKey() {
		return this.key;
	}
}
