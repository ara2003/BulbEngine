package com.greentree.bulbgl.input.event;

import com.greentree.event.Event;

/** @author Arseny Latyshev */
public final class TextEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final String test;
	
	public TextEvent(final String test) {
		this.test = test;
	}
	
	public String getTest() {
		return this.test;
	}
}
