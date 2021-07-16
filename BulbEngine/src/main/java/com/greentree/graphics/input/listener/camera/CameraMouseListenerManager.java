package com.greentree.graphics.input.listener.camera;

import com.greentree.engine.Cameras;
import com.greentree.event.Event;
import com.greentree.event.OneListenerListenerManager;
import com.greentree.graphics.input.event.MouseClickEvent;
import com.greentree.graphics.input.event.MouseMovedEvent;

/** @author Arseny Latyshev */
public class CameraMouseListenerManager extends OneListenerListenerManager<Event, CameraMouseListener> {

	private static final long serialVersionUID = 1L;

	public CameraMouseListenerManager() {
		super(CameraMouseListener.class);
	}

	@Override
	public void event(final CameraMouseListener l, final Event event) {
		if(event instanceof MouseMovedEvent) {
			final MouseMovedEvent mouseevent = (MouseMovedEvent) event;
			switch(mouseevent.getEventType()) {
				case mouseDragged:
					l.mouseDragged(getX(mouseevent.getX1()),
							getY(mouseevent.getY1()), getX(mouseevent.getX2()), getY(mouseevent.getY2()));
					break;
				case mouseMoved:
					l.mouseMoved(getX(mouseevent.getX1()),
							getY(mouseevent.getY1()), getX(mouseevent.getX2()), getY(mouseevent.getY2()));
					break;
			}
		}
		if(event instanceof MouseClickEvent) {
			final MouseClickEvent mouseevent = (MouseClickEvent) event;
			switch(mouseevent.getEventType()) {
				case mousePress:
					l.mousePress(mouseevent.getButton(), getX(mouseevent.getX()), getY(mouseevent.getY()));
					break;
				case mouseRelease:
					l.mouseRelease(mouseevent.getButton(), getX(mouseevent.getX()), getY(mouseevent.getY()));
					break;
				case mouseRepeat:
					l.mouseRepeat(mouseevent.getButton(), getX(mouseevent.getX()), getY(mouseevent.getY()));
					break;
			}
		}
	}

	private int getX(final int x) {
		return (int) Cameras.getMainCamera().WindowToCameraX(x);
	}

	private int getY(final int y) {
		return (int) Cameras.getMainCamera().WindowToCameraY(y);
	}

	@Override
	protected boolean isUse(CameraMouseListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

}
