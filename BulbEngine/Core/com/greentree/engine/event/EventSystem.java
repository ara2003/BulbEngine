package com.greentree.engine.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.greentree.engine.Debug;
import com.greentree.engine.Timer;
import com.greentree.util.OneClassSet;

public class EventSystem implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Object eventLock = new Object();
	private final Queue<Event> eventQuery;
	private final OneClassSet<ListenerManager> listenerManagers;
	private final List<Listener> listeners;
	private final Timer timer = new Timer();
	
	public EventSystem() {
		listeners = new ArrayList<>();
		listenerManagers = new OneClassSet<>();
		eventQuery = new LinkedList<>();
	}
	
	public void addListener(final Listener listener) {
		listeners.add(listener);
		for(final ListenerManager l : listenerManagers) l.addListener(listener);
	}
	
	public boolean addListenerManager(final ListenerManager listenerManager) {
		for(Listener listener : listeners)listenerManager.addListener(listener);
		return listenerManagers.add(listenerManager);
	}
	
	@Override
	public String toString() {
		return "EventSystem [listeners=" + listenerManagers + "]";
	}
	
	public void update() {
		synchronized(eventLock) {
			while(!eventQuery.isEmpty()) {
				Event event = eventQuery.remove();
				for(final ListenerManager l : new ArrayList<>(listenerManagers)) {
					timer.start(0);
					l.event(event);
					Debug.addTime(l.getClass().getSimpleName(), timer.finish(0) + "");
				}
			}
		}
	}

	public void eventNoQueue(Event event) {
		for(final ListenerManager l : listenerManagers)l.event(event);
	}

	public void event(Event event) {
		eventQuery.add(event);
	}
}
