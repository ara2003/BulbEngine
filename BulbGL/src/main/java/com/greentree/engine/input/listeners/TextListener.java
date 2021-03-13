package com.greentree.engine.input.listeners;

import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;
import com.greentree.engine.input.TextListenerManager;

/**
 * @author Arseny Latyshev
 *
 */
@FunctionalInterface
@necessarilyListenerManagers(TextListenerManager.class)
public interface TextListener extends Listener {
	
	public void write(String text);
	
}
