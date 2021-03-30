package com.greentree.bulbgl.input.listener.manager;

import com.greentree.bulbgl.input.event.TextEvent;
import com.greentree.bulbgl.input.listener.TextListener;
import com.greentree.event.OneListenerListenerManager;

/** @author Arseny Latyshev */
public class TextListenerManager extends OneListenerListenerManager<TextEvent, TextListener> {
	
	private static final long serialVersionUID = 1L;
	
	public TextListenerManager() {
		super(TextListener.class);
	}
	
	@Override
	public void event(final TextListener listener, final TextEvent event) {
		listener.write(event.getTest());
	}
}
