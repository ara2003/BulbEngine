package com.greentree.bulbgl.input.event;

import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
public final class MouseClickEvent implements MouseEvent {
	
	private static final long serialVersionUID = 1L;
	private int button, x, y;
	private EventType type;
	
	private MouseClickEvent(final EventType type, final int button, final int x, final int y) {
		this.type   = type;
		this.button = button;
		this.x      = x;
		this.y      = y;
	}
	
	public static MouseClickEvent getInstanse(final EventSystem eventSystem, final EventType type, final int button, final int x,
			final int y) {
		MouseClickEvent event = eventSystem.get(MouseClickEvent.class);
		if(event == null) event = new MouseClickEvent(type, button, x, y);
		else event.reset(type, button, x, y);
		return event;
	}
	
	public int getButton() {
		return this.button;
	}
	
	public EventType getEventType() {
		return this.type;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public MouseClickEvent reset(final EventType eventType, final int button, final int x, final int y) {
		this.type   = eventType;
		this.x      = x;
		this.y      = y;
		this.button = button;
		return this;
	}
	
	public enum EventType{
		mousePress,mouseRelease,mouseRepeat;
	}
}
