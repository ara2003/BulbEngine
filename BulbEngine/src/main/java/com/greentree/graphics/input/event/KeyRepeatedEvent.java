package com.greentree.graphics.input.event;

import com.greentree.event.EventSystem;

@Deprecated
/** @author Arseny Latyshev */
public final class KeyRepeatedEvent implements KeyEvent {

	private int key = 0;
	private static final long serialVersionUID = 1L;

	public KeyRepeatedEvent(final int key) {
		this.key = key;
	}

	public static KeyRepeatedEvent getInstanse(final EventSystem eventSystem, final int key) {
		if(eventSystem == null)return new KeyRepeatedEvent(key);
		KeyRepeatedEvent event = eventSystem.get(KeyRepeatedEvent.class);
		if(event == null) event = new KeyRepeatedEvent(key);
		else event.key = key;
		return event;
	}

	public int getKey() {
		return key;
	}
}
