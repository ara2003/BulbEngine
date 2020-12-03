package com.greentree.engine.phisic;

import com.greentree.engine.event.Event;
import com.greentree.engine.object.GameObject;

public class ColliderEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final GameObject object1, object2;
	private final EventType type;
	
	public ColliderEvent(final EventType type, final GameObject object1, final GameObject object2) {
		this.object1 = object1;
		this.object2 = object2;
		this.type = type;
	}

	public EventType getEventType() {
		return type;
	}

	public GameObject getObject1() {
		return object1;
	}

	public GameObject getObject2() {
		return object2;
	}

	@Override
	public String toString() {
		return object1 + " " + object2;
	}

	public enum EventType{
		exit,stay,enter;
	}
}
