package com.greentree.bulbgl.input.event;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.EventSystem;

/**
 * @author Arseny Latyshev
 *
 */
public final class MouseClickEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private int button, x, y;
	private EventType type;
	
	private MouseClickEvent(final EventType type, final int button, final int x, final int y) {
		this.type = type;
		this.button = button;
		this.x = x;
		this.y = y;
	}
	
	public int getButton() {
		return button;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public enum EventType{
		mousePress, mouseRelease, mouseRepeat;
	}

	public MouseClickEvent reset(EventType eventType, int button, int x, int y) {
		this.type = eventType;
		this.x = x;
		this.y = y;
		this.button = button;
		return this;
	}

	public static MouseClickEvent getInstanse(EventSystem eventSystem, EventType type, int button, int x, int y) {
		MouseClickEvent event = eventSystem.get(MouseClickEvent.class);
		if(event == null)
			event = new MouseClickEvent(type, button, x, y);
		else
			event.reset(type, button, x, y);
		return event;
	}
	
}
