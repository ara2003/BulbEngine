package com.greentree.engine.input;

import com.greentree.engine.event.Event;

public class MouseEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final int button, x1, y1, x2, y2;
	private final EventType type;
	
	public MouseEvent(final EventType type, final int button, final int x, final int y) {
		this(type, button, x, y, x, y);
	}
	
	public MouseEvent(final EventType type, final int x1, final int y1, final int x2, final int y2) {
		this(type, -1, x1, y1, x2, y2);
	}
	
	private MouseEvent(final EventType type, final int button, final int x1, final int y1, final int x2, final int y2) {
		this.type = type;
		this.button = button;
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
	
	public int getX() {
		return x1;
	}
	
	public int getX1() {
		return x1;
	}
	
	public int getX2() {
		return x2;
	}
	
	public int getY() {
		return y1;
	}
	
	public int getY1() {
		return y1;
	}
	
	public int getY2() {
		return y2;
	}
	
	public enum EventType{
		mouseDragged,mouseMoved,mousePressed,mouseReleased;
	}
}
