package com.greentree.engine.event;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.greentree.engine.Debug;
import com.greentree.engine.Timer;
import com.greentree.util.OneClassSet;

public class EventSystem {
	
	private final Object eventLock = new Object();
	private final Queue<Event> eventQueue;
	private final Set<ListenerManager> listeners;
	private final Timer timer = new Timer();
	
	public EventSystem() {
		listeners = new OneClassSet<>();
		eventQueue = new LinkedBlockingQueue<>();
	}
	
	public void addListener(final Listener listener) {
		for(final ListenerManager l : listeners) l.addListener(listener);
	}
	
	public boolean addListenerManager(final ListenerManager listener) {
		return listeners.add(listener);
	}
	
	public void event(final Event event) {
		eventQueue.add(event);
	}
	
	public void eventNoQueue(final Event event) {
		synchronized(eventLock) {
			for(final ListenerManager l : listeners) l.event(event);
		}
	}
	@Override
	public String toString() {
		return "EventSystem [listeners=" + listeners + "]";
	}
	
	public void update() {
		synchronized(eventLock) {
			if(!eventQueue.isEmpty()) Debug.addEvent(getClass().getSimpleName(), eventQueue.toString());
			while(!eventQueue.isEmpty()) {
				final Event e = eventQueue.remove();
				for(final ListenerManager l : listeners) {
					timer.start(0);
					l.event(e);
					Debug.addTime(l.getClass().getSimpleName(), timer.finish(0) + "");
				}
			}
		}
	}
}
