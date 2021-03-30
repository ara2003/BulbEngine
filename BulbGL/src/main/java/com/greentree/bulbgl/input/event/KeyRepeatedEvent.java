package com.greentree.bulbgl.input.event;

import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
public final class KeyRepeatedEvent implements KeyEvent {
	
	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyRepeatedEvent(final int key) {
		this.key = key;
	}
	
	@SuppressWarnings("exports")
	public static KeyRepeatedEvent getInstanse(final EventSystem eventSystem, final int key) {
		KeyRepeatedEvent event = eventSystem.get(KeyRepeatedEvent.class);
		if(event == null) event = new KeyRepeatedEvent(key);
		else event.key = key;
		return event;
	}
	
	public int getKey() {
		return this.key;
	}
}
