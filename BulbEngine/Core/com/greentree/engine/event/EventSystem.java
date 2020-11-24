package com.greentree.engine.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventSystem {
	
	private final Object eventLock = new Object();
	private final Queue<Event> eventQueue;
	private final List<ListenerManager> listeners;

	public EventSystem() {
		listeners = new ArrayList<>();
		eventQueue = new LinkedBlockingQueue<>();
	}
	
	public void addListener(final Listener listener) {
		for(final ListenerManager l : listeners) l.addListener(listener);
	}
	
	public void addListenerManager(final ListenerManager listener) {
		listeners.add(listener);
	}

	public void event(final Event event) {
		new Thread(()-> {
			synchronized(eventLock) {
				eventQueue.add(event);
			}
		}).start();
	}
	
	public void update() {
		synchronized(eventLock) {
			eventQueue.forEach(e-> {
				for(final ListenerManager l : listeners) l.event(e);
			});
			eventQueue.clear();
		}
	}
}
