package com.greentree.bulbgl.input.listener;

import com.greentree.bulbgl.input.listener.manager.TextListenerManager;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.NecessarilyListenerManagers;

/**
 * @author Arseny Latyshev
 *
 */
@FunctionalInterface
@NecessarilyListenerManagers(TextListenerManager.class)
public interface TextListener extends Listener {
	
	public void write(String text);
	
}
