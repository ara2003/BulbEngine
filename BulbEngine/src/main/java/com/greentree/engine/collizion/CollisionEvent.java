package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.event.Event;

public class CollisionEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final ColliderComponent object1, object2;
	private final EventType type;
	
	public CollisionEvent(final EventType type, final ColliderComponent object1, final ColliderComponent object2) {
		this.object1 = object1;
		this.object2 = object2;
		this.type    = type;
	}
	
	public EventType getEventType() {
		return this.type;
	}
	
	public ColliderComponent getObject1() {
		return this.object1;
	}
	
	public ColliderComponent getObject2() {
		return this.object2;
	}
	
	@Override
	public String toString() {
		return "[Collider " + this.type + " " + this.object1 + " " + this.object2 + "]";
	}
	
	public enum EventType{
		exit,stay,enter;
	}
}
