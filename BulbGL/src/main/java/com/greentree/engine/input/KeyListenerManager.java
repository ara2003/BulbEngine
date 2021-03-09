package com.greentree.engine.input;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.ListenerManager;
import com.greentree.engine.input.listeners.KeyListener;

public class KeyListenerManager extends ListenerManager {
	
	private static final long serialVersionUID = 1L;
	private final List<KeyListener> listeners = new ArrayList<>();
	
	public KeyListenerManager() {
		super(KeyEvent.class, KeyPressedEvent.class, KeyRepaeasedEvent.class);
	}
	
	@Override
	public boolean addListener(final Listener listener) {
		if(listener instanceof KeyListener) 
			return listeners.add((KeyListener) listener);
		else
			return false;
	}
	
	@Override
	public void event(@SuppressWarnings("exports") final Event event) {
		if(event instanceof KeyPressedEvent) {
			final KeyPressedEvent keyevent = (KeyPressedEvent) event;
			for(final KeyListener l : listeners) l.keyPressed(keyevent.getCode());
		}else if(event instanceof KeyRepaeasedEvent) {
			final KeyRepaeasedEvent keyevent = (KeyRepaeasedEvent) event;
			for(final KeyListener l : listeners) l.keyReleased(keyevent.getCode());
		}
	}
}
