package com.greentree.engine.input;

import com.greentree.engine.event.Event;


/**
 * @author Arseny Latyshev
 *
 */
public class KeyPressedEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private int key = 0;
	
	public KeyPressedEvent(final int key) {
		this.key = key;
	}
	
	public int getCode() {
		return key;
	}

	public KeyPressedEvent reset(int key) {
		this.key = key;
		return this;
	}
	
}
