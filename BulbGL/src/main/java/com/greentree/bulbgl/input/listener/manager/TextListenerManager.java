package com.greentree.bulbgl.input.listener.manager;


import com.greentree.bulbgl.input.event.TextEvent;
import com.greentree.bulbgl.input.listener.TextListener;
import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerListenerManager;


/**
 * @author Arseny Latyshev
 *
 */
public class TextListenerManager extends OneListenerListenerManager<TextListener> {
	private static final long serialVersionUID = 1L;

	public TextListenerManager() {
		super(TextListener.class);
	}

	@Override
	public void event(Event event) {
		if(event instanceof TextEvent) 
			for(TextListener listener : listeners)listener.write(((TextEvent) event).getTest());
	}
}
