package com.greentree.bulbgl.input.event;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;


/**
 * @author Arseny Latyshev
 *
 */
public final class KeyRepeatedEvent implements Event {
	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyRepeatedEvent(final int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
	
	public static KeyRepeatedEvent getInstanse(EventSystem eventSystem, final int key) {
		KeyRepeatedEvent event = eventSystem.get(KeyRepeatedEvent.class);
		if(event == null)
			event = new KeyRepeatedEvent(key);
		else 
			event.key = key;
		return event;
	}
	
}
