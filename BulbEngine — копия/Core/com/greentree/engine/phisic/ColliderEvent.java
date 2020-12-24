package com.greentree.engine.phisic;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.event.Event;

public class ColliderEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final ColliderComponent object1, object2;
	private final EventType type;
	
	public ColliderEvent(final EventType type, final ColliderComponent object1, final ColliderComponent object2) {
		this.object1 = object1;
		this.object2 = object2;
		this.type = type;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	public ColliderComponent getObject1() {
		return object1;
	}
	
	public ColliderComponent getObject2() {
		return object2;
	}
	
	@Override
	public String toString() {
		return "[Collider " +type + " " + object1 + " " + object2 + "]";
	}
	
	public enum EventType{
		exit,stay,enter;
	}
}
