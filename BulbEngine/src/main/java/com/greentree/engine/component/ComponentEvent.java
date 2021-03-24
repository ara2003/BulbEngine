package com.greentree.engine.component;


import com.greentree.engine.event.Event;
import com.greentree.engine.object.GameComponent;
/**
 * @author Arseny Latyshev
 *
 */
public class ComponentEvent implements Event {

	private static final long serialVersionUID = 1L;
	private final GameComponent component;
	private final EventType type;
	
	public ComponentEvent(final EventType type, final GameComponent component) {
		this.component = component;
		this.type    = type;
	}
	
	public EventType getEventType() {
		return this.type;
	}
	
	public GameComponent getComponent() {
		return component;
	}
	
	@Override
	public String toString() {
		return "[Collider " + this.type + " " + this.component + "]";
	}
	
	public enum EventType{
		create, destroy;
	}
	
	
}
