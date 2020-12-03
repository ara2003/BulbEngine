package com.greentree.engine.object;

import com.greentree.engine.event.Event;

public class GameObjectEvent implements Event {

	private static final long serialVersionUID = 1L;
	private final GameObject object;
	private final EventType type;

	GameObjectEvent(final EventType type, final GameObject object) {
		this.type = type;
		this.object = object;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	public GameObject getObject() {
		return object;
	}
	
	@Override
	public String toString() {
		return type + " " + object;
	}

	public enum EventType{
		create,destroy;
	}
}
