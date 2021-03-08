package com.greentree.engine.input;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.ListenerManager;

public class MouseListenerManager extends ListenerManager {
	
	private static final long serialVersionUID = 1L;
	private final List<MouseListener> listeners = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public MouseListenerManager() {
		super(MouseEvent.class);
	}
	
	@Override
	public void addListener(final Listener listener) {
		if(listener instanceof MouseListener) listeners.add((MouseListener) listener);
	}
	
	@Override
	public void event(final Event event) {
		if(event instanceof MouseEvent) {
			final MouseEvent mouseevent = (MouseEvent) event;
			switch(mouseevent.getEventType()) {
				case mousePressed:
					for(final MouseListener l : listeners)
						l.mousePressed(mouseevent.getButton(), mouseevent.getX(), mouseevent.getY());
					break;
				case mouseReleased:
					for(final MouseListener l : listeners)
						l.mouseReleased(mouseevent.getButton(), mouseevent.getX(), mouseevent.getY());
					break;
				case mouseDragged:
					for(final MouseListener l : listeners)
						l.mouseDragged(mouseevent.getX1(), mouseevent.getY1(), mouseevent.getX2(), mouseevent.getY2());
					break;
				case mouseMoved:
					for(final MouseListener l : listeners)
						l.mouseMoved(mouseevent.getX1(), mouseevent.getY1(), mouseevent.getX2(), mouseevent.getY2());
					break;
			}
		}
	}
}
