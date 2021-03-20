package com.greentree.bulbgl.input.util;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;


/**
 * @author Arseny Latyshev
 *
 */
public final class KeyPressedEvent implements Event {
	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyPressedEvent(final int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
	
	public static KeyPressedEvent getInstanse(EventSystem eventSystem, final int key) {
		KeyPressedEvent event = eventSystem.get(KeyPressedEvent.class);
		if(event == null)
			event = new KeyPressedEvent(key);
		else 
			event.key = key;
		return event;
	}
	
}
