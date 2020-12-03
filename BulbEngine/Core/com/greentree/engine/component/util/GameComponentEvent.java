package com.greentree.engine.component.util;

import com.greentree.engine.event.Event;
import com.greentree.engine.object.GameComponent;

/*
 * Event discribe create/derstroy of object his hav this component
 */
public class GameComponentEvent implements Event {

	private static final long serialVersionUID = 1L;
	private final GameComponent component;
	private final EventType type;

	public GameComponentEvent(final EventType type, final GameComponent component) {
		this.type = type;
		this.component = component;
	}
	
	public GameComponent getComponent() {
		return component;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + type + " " + component.getClass().getSimpleName();
	}

	public enum EventType{
		create,destroy;
	}
}
