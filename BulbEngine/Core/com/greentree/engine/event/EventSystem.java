package com.greentree.engine.event;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.greentree.engine.Debug;
import com.greentree.engine.Time;

public class EventSystem {

	private final Object eventLock = new Object();
	private final Queue<Event> eventQueue;
	private final Set<ListenerManager> listeners;

	public EventSystem() {
		listeners = new HashSet<>();
		eventQueue = new LinkedBlockingQueue<>();
	}

	public void addListener(final Listener listener) {
		for(final ListenerManager l : listeners) l.addListener(listener);
	}

	public void addListenerManager(final ListenerManager listener) {
		listeners.add(listener);
	}

	public void event(final Event event) {
		eventQueue.add(event);
	}

	public void eventNoQueue(final Event event) {
		synchronized(eventLock) {
			for(final ListenerManager l : listeners) l.event(event);
		}
	}
	
	public void update() {
		synchronized(eventLock) {
			if(!eventQueue.isEmpty())Debug.addEvent(getClass().getSimpleName(), eventQueue.toString());
			while(!eventQueue.isEmpty()) {
				final Event e = eventQueue.remove();
				for(final ListenerManager l : listeners) {
					Time.start(0);
					l.event(e);
					Debug.addTime(l.getClass().getSimpleName(), Time.finish(0) + "");
				}
			}
		}
	}
}
