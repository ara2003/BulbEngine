package com.greentree.engine.input;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerMutiEventListenerManager;
import com.greentree.engine.input.listeners.KeyListener;

public class KeyListenerManager extends OneListenerMutiEventListenerManager<KeyListener> {
	
	private static final long serialVersionUID = 1L;
	
	public KeyListenerManager() {
		super(KeyListener.class, KeyPressedEvent.class, KeyRepaeasedEvent.class);
	}
	
	@Override
	public void event0(final Event event) {
		if(event instanceof KeyPressedEvent) {
			final KeyPressedEvent keyevent = (KeyPressedEvent) event;
			for(final KeyListener l : listeners) l.keyPressed(keyevent.getKey());
		}else if(event instanceof KeyRepaeasedEvent) {
			final KeyRepaeasedEvent keyevent = (KeyRepaeasedEvent) event;
			for(final KeyListener l : listeners) l.keyReleased(keyevent.getKey());
		}
	}
}
