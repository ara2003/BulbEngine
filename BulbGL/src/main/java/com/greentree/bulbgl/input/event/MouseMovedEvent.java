package com.greentree.bulbgl.input.event;

import com.greentree.event.EventSystem;

/** @author Arseny Latyshev */
public final class MouseMovedEvent implements MouseEvent {
	
	private static final long serialVersionUID = 1L;
	private int button, x1, y1, x2, y2;
	private EventType type;
	
	private MouseMovedEvent(final EventType type, final int x1, final int y1, final int x2, final int y2) {
		this.type = type;
		this.x1   = x1;
		this.y1   = y1;
		this.x2   = x2;
		this.y2   = y2;
	}
	
	@SuppressWarnings("exports")
	public static MouseMovedEvent getInstanse(final EventSystem eventSystem, final EventType type, final int x1, final int y1,
			final int x2, final int y2) {
		MouseMovedEvent event = eventSystem.get(MouseMovedEvent.class);
		if(event == null) event = new MouseMovedEvent(type, x1, y1, x2, y2);
		else event.reset(type, x1, y1, x2, y2);
		return event;
	}
	
	public int getButton() {
		return this.button;
	}
	
	public EventType getEventType() {
		return this.type;
	}
	
	public int getX1() {
		return this.x1;
	}
	
	public int getX2() {
		return this.x2;
	}
	
	public int getY1() {
		return this.y1;
	}
	
	public int getY2() {
		return this.y2;
	}
	
	public MouseMovedEvent reset(final EventType eventType, final int x1, final int y1, final int x2, final int y2) {
		this.type = eventType;
		this.x1   = x1;
		this.x2   = x2;
		this.y1   = y1;
		this.y2   = y2;
		return this;
	}
	
	public enum EventType{
		mouseDragged,mouseMoved;
	}
}
