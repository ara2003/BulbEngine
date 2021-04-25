package com.greentree.engine;

import com.greentree.engine.core.Events;
import com.greentree.graphics.input.event.KeyPressedEvent;
import com.greentree.graphics.input.event.KeyRepeatedEvent;
import com.greentree.graphics.input.event.KeyRepleasedEvent;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class KeyBoard {
	
	static {
		Windows.window.getKeyPress().addListener(e -> Events.event(KeyPressedEvent.getInstanse(Events.getEventsystem(), e)));
		Windows.window.getKeyRelease().addListener(e -> Events.event(KeyRepleasedEvent.getInstanse(Events.getEventsystem(), e)));
		Windows.window.getKeyRepeat().addListener(e -> Events.event(KeyRepeatedEvent.getInstanse(Events.getEventsystem(), e)));
	}
	
	public static void init(){
	}
	
}
