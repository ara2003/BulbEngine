package com.greentree.graphics.input.event;

import com.greentree.event.EventSystem;

@Deprecated
/** @author Arseny Latyshev */
public final class MouseClickEvent implements MouseEvent {

	private int button, x, y;
	private EventType type;
	private static final long serialVersionUID = 1L;

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

	public MouseClickEvent reset(final EventType eventType, final int button, final int x, final int y) {
		type   = eventType;
		this.x      = x;
		this.y      = y;
		this.button = button;
		return this;
	}

	public enum EventType{
		mousePress,mouseRelease,mouseRepeat;
	}
}
