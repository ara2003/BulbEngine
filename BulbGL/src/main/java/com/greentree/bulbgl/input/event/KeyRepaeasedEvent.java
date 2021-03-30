package com.greentree.bulbgl.input.event;

import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
public final class KeyRepaeasedEvent implements KeyEvent {
	
	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyRepaeasedEvent(final int key) {
		this.key = key;
	}
	
	@SuppressWarnings("exports")
	public static KeyRepaeasedEvent getInstanse(final EventSystem eventSystem, final int key) {
		KeyRepaeasedEvent event = eventSystem.get(KeyRepaeasedEvent.class);
		if(event == null) event = new KeyRepaeasedEvent(key);
		else event.key = key;
		return event;
	}
	
	public int getKey() {
		return this.key;
	}
}
