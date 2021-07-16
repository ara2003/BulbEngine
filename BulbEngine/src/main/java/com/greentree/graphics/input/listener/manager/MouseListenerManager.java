package com.greentree.graphics.input.listener.manager;

import com.greentree.event.OneListenerListenerManager;
import com.greentree.graphics.input.event.MouseClickEvent;
import com.greentree.graphics.input.event.MouseEvent;
import com.greentree.graphics.input.event.MouseMovedEvent;
import com.greentree.graphics.input.listener.MouseListener;

@Deprecated
public class MouseListenerManager extends OneListenerListenerManager<MouseEvent, MouseListener> {

	private static final long serialVersionUID = 1L;

	public MouseListenerManager() {
		super(MouseListener.class);
	}

	@Override
	public void event(MouseListener l, final MouseEvent mouseevent) {
		if(mouseevent instanceof MouseMovedEvent) {
			MouseMovedEvent	event = (MouseMovedEvent)mouseevent;
			switch(event.getEventType()) {
				case mouseDragged:
					l.mouseDragged(event.getX1(), event.getY1(), event.getX2(), event.getY2());
					break;
				case mouseMoved:
					l.mouseMoved(event.getX1(), event.getY1(), event.getX2(), event.getY2());
					break;
			}
			return;
		}
		if(mouseevent instanceof MouseClickEvent) {
			MouseClickEvent	event = (MouseClickEvent)mouseevent;
			switch(event.getEventType()) {
				case mousePress:
					l.mousePress(event.getButton(), event.getX(), event.getY());
					break;
				case mouseRelease:
					l.mouseRelease(event.getButton(), event.getX(), event.getY());
					break;
				case mouseRepeat:
					l.mouseRepeat(event.getButton(), event.getX(), event.getY());
					break;
			}
		}
	}


}
