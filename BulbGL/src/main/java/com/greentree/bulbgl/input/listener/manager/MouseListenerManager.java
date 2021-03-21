package com.greentree.bulbgl.input.listener.manager;

import com.greentree.bulbgl.input.event.MouseClickEvent;
import com.greentree.bulbgl.input.event.MouseMovedEvent;
import com.greentree.bulbgl.input.listener.MouseListener;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerListenerManager;

public class MouseListenerManager extends OneListenerListenerManager<MouseListener> {
	
	private static final long serialVersionUID = 1L;
	
	public MouseListenerManager() {
		super(MouseListener.class);
	}

	@Override
	public void event(final Event event) {
		if(event instanceof MouseMovedEvent) {
			final MouseMovedEvent mouseevent = (MouseMovedEvent) event;
			switch(mouseevent.getEventType()) {
				case mouseDragged:
					for(final MouseListener l : listeners)
						l.mouseDragged(mouseevent.getX1(), mouseevent.getY1(), mouseevent.getX2(), mouseevent.getY2());
				break;
				case mouseMoved:
					for(final MouseListener l : listeners)
						l.mouseMoved(mouseevent.getX1(), mouseevent.getY1(), mouseevent.getX2(), mouseevent.getY2());
				break;
			}
			return;
		}	
		if(event instanceof MouseClickEvent) {
			final MouseClickEvent mouseevent = (MouseClickEvent) event;
			switch(mouseevent.getEventType()) {
				case mousePress:
					for(final MouseListener l : listeners)
						l.mousePress(mouseevent.getButton(), mouseevent.getX(), mouseevent.getY());
				break;
				case mouseRelease:
					for(final MouseListener l : listeners)
						l.mouseRelease(mouseevent.getButton(), mouseevent.getX(), mouseevent.getY());
				break;
				case mouseRepeat:
					for(final MouseListener l : listeners)
						l.mouseRepeat(mouseevent.getButton(), mouseevent.getX(), mouseevent.getY());
				break;
			}
		}
	}


}
