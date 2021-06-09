package com.greentree.graphics.input.event;

import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
public final class KeyPressedEvent implements KeyEvent {

	private static final long serialVersionUID = 1L;
	public static KeyPressedEvent getInstanse(final EventSystem eventSystem, final int key) {
		KeyPressedEvent event = eventSystem.get(KeyPressedEvent.class);
		if(event == null) event = new KeyPressedEvent(key);
		else event.key = key;
		return event;
	}

	private int key = 0;

	public KeyPressedEvent(final int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
}
