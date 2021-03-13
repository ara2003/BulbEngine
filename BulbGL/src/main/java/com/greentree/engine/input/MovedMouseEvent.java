package com.greentree.engine.input;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;

/**
 * @author Arseny Latyshev
 *
 */
public final class MovedMouseEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private int button, x1, y1, x2, y2;
	private EventType type;
	
	private MovedMouseEvent(final EventType type, final int x1, final int y1, final int x2, final int y2) {
		this.type = type;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public int getButton() {
		return button;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	public int getX1() {
		return x1;
	}
	
	public int getX2() {
		return x2;
	}
	
	public int getY1() {
		return y1;
	}
	
	public int getY2() {
		return y2;
	}
	
	public enum EventType{
		mouseDragged,mouseMoved;
	}

	public MovedMouseEvent reset(EventType eventType, int x1, int y1, int x2, int y2) {
		this.type = eventType;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		return this;
	}

	public static MovedMouseEvent getInstanse(EventSystem eventSystem, EventType type, int x1, int y1, int x2, int y2) {
		MovedMouseEvent event = eventSystem.get(MovedMouseEvent.class);
		if(event == null)
			event = new MovedMouseEvent(type, x1, y1, x2, y2);
		else
			event.reset(type, x1, y1, x2, y2);
		return event;
	}
	
}
