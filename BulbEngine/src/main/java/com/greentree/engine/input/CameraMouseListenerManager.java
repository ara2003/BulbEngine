package com.greentree.engine.input;

import com.greentree.bulbgl.input.event.MouseClickEvent;
import com.greentree.bulbgl.input.event.MouseMovedEvent;
import com.greentree.engine.Game;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerListenerManager;


/**
 * @author Arseny Latyshev
 *
 */
public class CameraMouseListenerManager extends OneListenerListenerManager<CameraMouseListener> {
	private static final long serialVersionUID = 1L;

	private int getX(int x) {
		return Game.getMainCamera().WindowToCameraX(x);
	}
	private int getY(int y) {
		return Game.getMainCamera().WindowToCameraY(y);
}
	
	public CameraMouseListenerManager() {
		super(CameraMouseListener.class);
	}

	@Override
	public void event(Event event) {
		if(event instanceof MouseMovedEvent) {
			final MouseMovedEvent mouseevent = (MouseMovedEvent) event;
			switch(mouseevent.getEventType()) {
				case mouseDragged:
					for(final CameraMouseListener l : listeners)
						l.mouseDragged(getX(mouseevent.getX1()), getY(mouseevent.getY1()), getX(mouseevent.getX2()), getY(mouseevent.getY2()));
				break;
				case mouseMoved:
					for(final CameraMouseListener l : listeners)
						l.mouseMoved(getX(mouseevent.getX1()), getY(mouseevent.getY1()), getX(mouseevent.getX2()), getY(mouseevent.getY2()));
				break;
			}
			return;
		}	
		if(event instanceof MouseClickEvent) {
			final MouseClickEvent mouseevent = (MouseClickEvent) event;
			switch(mouseevent.getEventType()) {
				case mousePress:
					for(final CameraMouseListener l : listeners)
						l.mousePress(mouseevent.getButton(), getX(mouseevent.getX()), getY(mouseevent.getY()));
				break;
				case mouseRelease:
					for(final CameraMouseListener l : listeners)
						l.mouseRelease(mouseevent.getButton(), getX(mouseevent.getX()), getY(mouseevent.getY()));
				break;
				case mouseRepeat:
					for(final CameraMouseListener l : listeners)
						l.mouseRepeat(mouseevent.getButton(), getX(mouseevent.getX()), getY(mouseevent.getY()));
				break;
			}
		}
	}
}
