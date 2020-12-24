package com.greentree.engine.input;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.ListenerManager;

public class KeyListenerManager extends ListenerManager {
	
	private static final long serialVersionUID = 1L;
	private final List<KeyListener> listeners = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public KeyListenerManager() {
		super(KeyEvent.class);
	}
	
	@Override
	public void addListener(final Listener listener) {
		if(listener instanceof KeyListener) listeners.add((KeyListener) listener);
	}
	
	@Override
	public void event(final Event event) {
		if(event instanceof KeyEvent) {
			final KeyEvent keyevent = (KeyEvent) event;
			switch(keyevent.getEventType()) {
				case keyReleased:
					for(final KeyListener l : listeners) l.keyReleased(keyevent.getCode());
					break;
				case keyPressed:
					for(final KeyListener l : listeners) l.keyPressed(keyevent.getCode());
					break;
			}
		}
	}
}
