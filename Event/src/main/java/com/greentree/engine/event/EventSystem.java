package com.greentree.engine.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventSystem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Object eventLock = new Object();
	private final Queue<Event> eventQuery;
	private final Collection<ListenerManager> listenerManagers;
	private final List<Listener> listeners;
	
	public EventSystem() {
		listeners = new ArrayList<>();
		listenerManagers = new ArrayList<>();
		eventQuery = new LinkedList<>();
	}
	
	public void addListener(final Listener listener) {
		listeners.add(listener);
		for(final ListenerManager l : listenerManagers) l.addListener(listener);
	}

	public boolean addListenerManager(final ListenerManager listenerManager) {
		return addListenerManager(listenerManager, false);
	}
	public boolean addListenerManager(final ListenerManager listenerManager, boolean once) {
		if(once)for(ListenerManager manager : listenerManagers) {
			Class<? extends ListenerManager> cl1 = manager.getClass();
			Class<? extends ListenerManager> cl2 = listenerManager.getClass();
			if(cl1.isAssignableFrom(cl2))return false;
			if(cl2.isAssignableFrom(cl1))return false;
		}
		
		for(Listener listener : listeners) listenerManager.addListener(listener);
		return listenerManagers.add(listenerManager);
	}
	
	public void event(Event event) {
		eventQuery.add(event);
	}
	
	public void eventNoQueue(Event event) {
		for(final ListenerManager l : listenerManagers) l.event(event);
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
					l.event(event);
				}
			}
		}
	}
}
