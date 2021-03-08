package com.greentree.engine.input;

import com.greentree.engine.event.Event;

public class KeyEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	int code = 0;
	private final EventType type;
	
	public KeyEvent(final EventType type, final int code) {
		this.type = type;
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	public enum EventType{
		keyPressed,keyReleased;
	}
}
