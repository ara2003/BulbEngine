package com.greentree.bulbgl.input.listener.manager;

import com.greentree.bulbgl.input.event.KeyPressedEvent;
import com.greentree.bulbgl.input.event.KeyRepaeasedEvent;
import com.greentree.bulbgl.input.event.KeyRepeatedEvent;
import com.greentree.bulbgl.input.listener.KeyListener;
import com.greentree.event.Event;
import com.greentree.event.OneListenerListenerManager;

public class KeyListenerManager extends OneListenerListenerManager<Event, KeyListener> {
	
	private static final long serialVersionUID = 1L;
	
	public KeyListenerManager() {
		super(KeyListener.class);
	}
	
	@Override
	public void event(final KeyListener l, final Event event) {
		if(event instanceof KeyPressedEvent) {
			final KeyPressedEvent keyevent = (KeyPressedEvent) event;
			l.keyPress(keyevent.getKey());
			return;
		}
		if(event instanceof KeyRepaeasedEvent) {
			final KeyRepaeasedEvent keyevent = (KeyRepaeasedEvent) event;
			l.keyRelease(keyevent.getKey());
			return;
		}
		if(event instanceof KeyRepeatedEvent) {
			final KeyRepeatedEvent keyevent = (KeyRepeatedEvent) event;
			l.keyRepeat(keyevent.getKey());
			return;
		}
	}
}
