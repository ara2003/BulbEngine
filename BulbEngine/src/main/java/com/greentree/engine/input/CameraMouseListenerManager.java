package com.greentree.engine.input;

import com.greentree.bulbgl.input.event.MouseClickEvent;
import com.greentree.bulbgl.input.event.MouseMovedEvent;
import com.greentree.engine.Cameras;
import com.greentree.event.Event;
import com.greentree.event.OneListenerListenerManager;

/** @author Arseny Latyshev */
public class CameraMouseListenerManager extends OneListenerListenerManager<Event, CameraMouseListener> {
	
	private static final long serialVersionUID = 1L;
	
	public CameraMouseListenerManager() {
		super(CameraMouseListener.class);
	}
	
	@Override
	public void event(CameraMouseListener l, final Event event) {
		if(event instanceof MouseMovedEvent) {
			final MouseMovedEvent mouseevent = (MouseMovedEvent) event;
			switch(mouseevent.getEventType()) {
				case mouseDragged:
					l.mouseDragged(this.getX(mouseevent.getX1()),
							this.getY(mouseevent.getY1()), this.getX(mouseevent.getX2()), this.getY(mouseevent.getY2()));
				break;
				case mouseMoved:
					l.mouseMoved(this.getX(mouseevent.getX1()),
							this.getY(mouseevent.getY1()), this.getX(mouseevent.getX2()), this.getY(mouseevent.getY2()));
				break;
			}
		}
		if(event instanceof MouseClickEvent) {
			final MouseClickEvent mouseevent = (MouseClickEvent) event;
			switch(mouseevent.getEventType()) {
				case mousePress:
						l.mousePress(mouseevent.getButton(), this.getX(mouseevent.getX()), this.getY(mouseevent.getY()));
				break;
				case mouseRelease:
						l.mouseRelease(mouseevent.getButton(), this.getX(mouseevent.getX()), this.getY(mouseevent.getY()));
				break;
				case mouseRepeat:
						l.mouseRepeat(mouseevent.getButton(), this.getX(mouseevent.getX()), this.getY(mouseevent.getY()));
				break;
			}
		}
	}
	
	private int getX(final int x) {
		return Cameras.getMainCamera().WindowToCameraX(x);
	}
	
	private int getY(final int y) {
		return Cameras.getMainCamera().WindowToCameraY(y);
	}
}
