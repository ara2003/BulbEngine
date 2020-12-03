package com.greentree.engine.object;

import com.greentree.engine.event.Event;

public class Message implements Event {

	private static final long serialVersionUID = 1L;
	private final String text;
	
	public Message(final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
