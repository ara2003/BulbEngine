package com.greentree.bulbgl.input.listeners;

import com.greentree.bulbgl.input.util.TextListenerManager;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;

/**
 * @author Arseny Latyshev
 *
 */
@FunctionalInterface
@necessarilyListenerManagers(TextListenerManager.class)
public interface TextListener extends Listener {
	
	public void write(String text);
	
}
