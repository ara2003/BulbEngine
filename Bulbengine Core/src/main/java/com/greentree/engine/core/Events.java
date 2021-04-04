package com.greentree.engine.core;

import com.greentree.event.Event;
import com.greentree.event.EventSystem;
import com.greentree.event.Listener;

/**
 * @author Arseny Latyshev
 *
 */
public class Events {
	
	private final static EventSystem eventSystem = new EventSystem();

	
	public static final EventSystem getEventsystem() {
		return eventSystem;
	}

	public static void addListener(final Listener listener) {
		Events.eventSystem.addListener(listener);
	}
	
	public static void event(final Event event) {
		Events.eventSystem.event(event);
	}

	static void update() {
		eventSystem.update();
	}

	public static void clear() {
		eventSystem.clear();
	}

}
