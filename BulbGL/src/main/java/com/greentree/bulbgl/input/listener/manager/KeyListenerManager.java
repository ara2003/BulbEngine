package com.greentree.bulbgl.input.listener.manager;

import com.greentree.bulbgl.input.event.KeyPressedEvent;
import com.greentree.bulbgl.input.event.KeyRepaeasedEvent;
import com.greentree.bulbgl.input.event.KeyRepeatedEvent;
import com.greentree.bulbgl.input.listener.KeyListener;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerListenerManager;

public class KeyListenerManager extends OneListenerListenerManager<KeyListener> {
	
	private static final long serialVersionUID = 1L;
	
	public KeyListenerManager() {
		super(KeyListener.class);
	}
	
	@Override
	public void event(final Event event) {
		if(event instanceof KeyPressedEvent) {
			final KeyPressedEvent keyevent = (KeyPressedEvent) event;
			for(final KeyListener l : listeners) l.keyPress(keyevent.getKey());
		}
		if(event instanceof KeyRepaeasedEvent) {
			final KeyRepaeasedEvent keyevent = (KeyRepaeasedEvent) event;
			for(final KeyListener l : listeners) l.keyRelease(keyevent.getKey());
		} 
		if(event instanceof KeyRepeatedEvent) {
			final KeyRepeatedEvent keyevent = (KeyRepeatedEvent) event;
			for(final KeyListener l : listeners) l.keyRepeat(keyevent.getKey());
		}
	}
}
