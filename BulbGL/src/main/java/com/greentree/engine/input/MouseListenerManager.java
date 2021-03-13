package com.greentree.engine.input;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerMutiEventListenerManager;
import com.greentree.engine.input.listeners.MouseListener;

public class MouseListenerManager extends OneListenerMutiEventListenerManager<MouseListener> {
	
	private static final long serialVersionUID = 1L;
	
	public MouseListenerManager() {
		super(MouseListener.class, MouseClickEvent.class, MovedMouseEvent.class);
	}

	@Override
	public void event0(final Event event) {
		if(event instanceof MovedMouseEvent) {
			final MovedMouseEvent mouseevent = (MovedMouseEvent) event;
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
				case mousePressed:
					for(final MouseListener l : listeners)
						l.mousePressed(mouseevent.getButton(), mouseevent.getX(), mouseevent.getY());
				break;
				case mouseReleased:
					for(final MouseListener l : listeners)
						l.mouseReleased(mouseevent.getButton(), mouseevent.getX(), mouseevent.getY());
				break;
			}
		}
	}


}
