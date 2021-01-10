package com.greentree.engine.event;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.greentree.engine.Debug;
import com.greentree.engine.Timer;
import com.greentree.util.OneClassSet;

public class EventSystem implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Object eventLock = new Object();
	private final Queue<Event> eventQuery;
	private final Set<ListenerManager> listeners;
	private final Timer timer = new Timer();
	
	public EventSystem() {
		listeners = new OneClassSet<>();
		eventQuery = new LinkedList<>();
	}
	
	public void addListener(final Listener listener) {
		for(final ListenerManager l : listeners) l.addListener(listener);
	}
	
	public boolean addListenerManager(final ListenerManager listener) {
		return listeners.add(listener);
	}
	
	@Override
	public String toString() {
		return "EventSystem [listeners=" + listeners + "]";
	}
	
	public void update() {
		synchronized(eventLock) {
			if(!eventQuery.isEmpty())System.out.println(eventQuery);
			for(Event e : eventQuery) {
				for(final ListenerManager l : listeners) {
					timer.start(0);
					l.event(e);
					Debug.addTime(l.getClass().getSimpleName(), timer.finish(0) + "");
				}
			}
			
		}
	}

	public void eventNoQueue(Event event) {
		for(final ListenerManager l : listeners)l.event(event);
	}

	public void event(Event event) {
		eventQuery.add(event);
	}
}
