package com.greentree.graphics.input.listener.manager;

import com.greentree.event.OneListenerListenerManager;
import com.greentree.graphics.input.event.TextEvent;
import com.greentree.graphics.input.listener.TextListener;

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
