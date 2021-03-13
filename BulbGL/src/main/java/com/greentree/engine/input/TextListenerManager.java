package com.greentree.engine.input;


import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerMutiEventListenerManager;
import com.greentree.engine.input.listeners.TextListener;


/**
 * @author Arseny Latyshev
 *
 */
public class TextListenerManager extends OneListenerMutiEventListenerManager<TextListener> {
	private static final long serialVersionUID = 1L;

	public TextListenerManager() {
		super(TextListener.class, TextEvent.class);
	}

	@Override
	public void event0(Event event) {
		if(event instanceof TextEvent) 
			for(TextListener listener : listeners)listener.write(((TextEvent) event).getTest());
	}
}
