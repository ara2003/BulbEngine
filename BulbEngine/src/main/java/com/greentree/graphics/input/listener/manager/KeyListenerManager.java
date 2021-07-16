package com.greentree.graphics.input.listener.manager;

import com.greentree.event.Event;
import com.greentree.event.OneListenerListenerManager;
import com.greentree.graphics.input.event.KeyPressedEvent;
import com.greentree.graphics.input.event.KeyRepeatedEvent;
import com.greentree.graphics.input.event.KeyRepleasedEvent;
import com.greentree.graphics.input.listener.KeyListener;

@Deprecated
public class KeyListenerManager extends OneListenerListenerManager<Event, KeyListener> {

	/**
	 * 
	 */
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
		if(event instanceof KeyRepleasedEvent) {
			final KeyRepleasedEvent keyevent = (KeyRepleasedEvent) event;
			l.keyRelease(keyevent.getKey());
			return;
		}
		if(event instanceof KeyRepeatedEvent) {
			final KeyRepeatedEvent keyevent = (KeyRepeatedEvent) event;
			l.keyRepeat(keyevent.getKey());
		}
	}
}
