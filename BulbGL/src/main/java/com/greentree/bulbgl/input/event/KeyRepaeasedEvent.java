package com.greentree.bulbgl.input.event;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;

/** @author Arseny Latyshev */
public final class KeyRepaeasedEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyRepaeasedEvent(final int key) {
		this.key = key;
	}

	@SuppressWarnings("exports")
	public static KeyRepaeasedEvent getInstanse(EventSystem eventSystem, final int key) {
		KeyRepaeasedEvent event = eventSystem.get(KeyRepaeasedEvent.class);
		if(event == null)
			event = new KeyRepaeasedEvent(key);
		else 
			event.key = key;
		return event;
	}
	
	public int getKey() {
		return key;
	}
	
}
