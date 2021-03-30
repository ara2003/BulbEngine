package com.greentree.engine.collizion.event;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.event.Event;

public class CollisionEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final ColliderComponent object1, object2;
	private final EventType type;
	
	public CollisionEvent(final EventType type, final ColliderComponent object1, final ColliderComponent object2) {
		this.type    = type;
		this.object1 = object1;
		this.object2 = object2;
	}
	
	public EventType getEventType() {
		return this.type;
	}
	
	public ColliderComponent getCollider1() {
		return this.object1;
	}
	
	public ColliderComponent getCollider2() {
		return this.object2;
	}
	
	@Override
	public String toString() {
		return "CollisionEvent [" + this.getEventType() + ", " + this.getCollider1() + ", " + this.getCollider2() + "]";
	}
	
	public enum EventType{
		EXIT,STAY,ENTER;
	}
}
