package com.greentree.graphics.input.event;

import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
@Deprecated
public final class KeyPressedEvent implements KeyEvent {

	private int key = 0;
	private static final long serialVersionUID = 1L;

	public KeyPressedEvent(final int key) {
		this.key = key;
	}

	public static KeyPressedEvent getInstanse(final EventSystem eventSystem, final int key) {
		KeyPressedEvent event = eventSystem.get(KeyPressedEvent.class);
		if(event == null) event = new KeyPressedEvent(key);
		else event.key = key;
		return event;
	}

	public int getKey() {
		return key;
	}
}
