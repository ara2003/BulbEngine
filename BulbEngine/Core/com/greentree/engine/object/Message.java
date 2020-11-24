package com.greentree.engine.object;

import com.greentree.engine.event.Event;


public class Message implements Event {
	private static final long serialVersionUID = 1L;
	
	private String text;
	
	public Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
