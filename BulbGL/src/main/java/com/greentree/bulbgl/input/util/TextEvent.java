package com.greentree.bulbgl.input.util;

import com.greentree.engine.event.Event;

/** @author Arseny Latyshev */
public final class TextEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final String test;
	
	public TextEvent(String test) {
		this.test = test;
	}
	
	public String getTest() {
		return test;
	}
	
	
}
