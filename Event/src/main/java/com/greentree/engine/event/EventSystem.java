package com.greentree.engine.event;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import com.greentree.util.ClassUtil;
import com.greentree.util.Log;
import com.greentree.util.OneClassSet;

public class EventSystem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Queue<Event> eventQuery;
	private final OneClassSet<ListenerManager> listenerManagers;
	private final Map<Class<? extends Event>, Queue<Event>> events;
	
	public EventSystem() {
		events = new HashMap<>();
		listenerManagers = new OneClassSet<>();
		eventQuery = new LinkedList<>();
	}

	protected void deleteEvent(Event event) {
		Queue<Event> q = events.remove(event.getClass());
		if(q == null)q = new LinkedList<>();
		q.add(event);
		events.put(event.getClass(), q);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Event> T get(Class<T> clazz){
		Objects.requireNonNull(clazz, "clazz is null");
		Queue<? extends Event> q = events.get(clazz);
		if(q == null)return null;
		if(q.isEmpty())return null;
		return (T) q.remove();
	}
	
	public boolean addListener(final Listener listener) {
		Objects.requireNonNull(listener, "listener is null");
		if(!addListener0(listener)) {
			tryAddNecessarily(listener.getClass());
		}
		return addListener0(listener);
	}
	
	private void tryAddNecessarily(Class<? extends Listener> clazz) {
		for(necessarilyListenerManagers an : ClassUtil.getAllAnnotations(clazz, necessarilyListenerManagers.class)) 
			for(Class<? extends ListenerManager> cl : an.value()) if(!listenerManagers.containsClass(cl)){
    			Constructor<? extends ListenerManager> constructor = null;
    			try {
    				constructor = cl.getConstructor();
    			}catch(NoSuchMethodException | SecurityException e) {
    				Log.warn(e);
    			}
    			ListenerManager lm = null;
    			try {
    				lm = constructor.newInstance();
    			}catch(InstantiationException | IllegalAccessException | IllegalArgumentException
    					| InvocationTargetException e) {
    				Log.warn(e);
    			}
    			addListenerManager(lm);
    		}
	}

	private boolean addListener0(final Listener listener) {
		boolean add = false;
		for(final ListenerManager l : listenerManagers) {
			if(l.addListener(listener))
			add = true;
		}
		return add;
	}

	public boolean addListenerManager(final ListenerManager listenerManager) {
		Objects.requireNonNull(listenerManager, "listenerManager is null");
		Class<? extends ListenerManager> cl2 = listenerManager.getClass();
		for(ListenerManager manager : listenerManagers) {
			Class<? extends ListenerManager> cl1 = manager.getClass();
			if(cl1.isAssignableFrom(cl2))return false;
			if(cl2.isAssignableFrom(cl1))return false;
		}
		return listenerManagers.add(listenerManager);
	}
	
	public void event(Event event) {
		Objects.requireNonNull(event, "event is null");
		eventQuery.add(event);
	}
	
	@Override
	public String toString() {
		return "EventSystem [listeners=" + listenerManagers + "]";
	}
	
	public void update() {
		synchronized(eventQuery) {
			while(!eventQuery.isEmpty()) {
				Event event = eventQuery.remove();
				for(final ListenerManager l : listenerManagers) {
					l.event(event);
				}
				deleteEvent(event);
			}
		}
	}
}
