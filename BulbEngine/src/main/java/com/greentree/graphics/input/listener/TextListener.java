package com.greentree.graphics.input.listener;

import com.greentree.event.Listener;
import com.greentree.event.NecessarilyListenerManagers;
import com.greentree.graphics.input.listener.manager.TextListenerManager;

/**
 * @author Arseny Latyshev
 *
 */
@FunctionalInterface
@NecessarilyListenerManagers(TextListenerManager.class)
public interface TextListener extends Listener {
	
	public void write(String text);
	
}
