package com.greentree.event;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import com.greentree.common.ClassUtil;
import com.greentree.common.Log;
import com.greentree.common.collection.ClassTree;
import com.greentree.common.collection.HashMapClassTree;
import com.greentree.common.collection.OneClassSet;

public class EventSystem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Queue<Event> eventQuery;
	private final OneClassSet<ListenerManager> listenerManagers;
	private final ClassTree<Event> eventPool;
	
	public EventSystem() {
		this.eventPool        = new HashMapClassTree<>();
		this.listenerManagers = new OneClassSet<>();
		this.eventQuery       = new LinkedList<>();
	}
	
	public boolean addListener(final Listener listener) {
		Objects.requireNonNull(listener, "listener is null");
		this.tryAddNecessarily(listener.getClass());
		boolean add = false;
		for(final ListenerManager l : this.listenerManagers) if(l.addListener(listener)) add = true;
		return add;
	}
	
	public boolean addListenerManager(final ListenerManager listenerManager) {
		Objects.requireNonNull(listenerManager, "listenerManager is null");
		final Class<?> cl2 = listenerManager.getClass();
		for(final ListenerManager manager : this.listenerManagers) {
			final Class<?> cl1 = manager.getClass();
			if(cl1.isAssignableFrom(cl2) && cl2.isAssignableFrom(cl1)) return false;
		}
		return this.listenerManagers.add(listenerManager);
	}
	
	protected void deleteEvent(final Event event) {
		eventPool.add(event);
	}
	
	public void event(final Event event) {
		Objects.requireNonNull(event, "event is null");
		this.eventQuery.add(event);
	}
	
	public <T extends Event> T get(final Class<T> clazz) {
		Objects.requireNonNull(clazz, "clazz is null");
		Queue<T> queue = this.eventPool.get(clazz);
		if(queue.isEmpty())return null;
		return queue.remove();
	}
	
	@Override
	public String toString() {
		return "EventSystem [listeners=" + this.listenerManagers + "]";
	}
	
	private void tryAddNecessarily(final Class<? extends Listener> clazz) {
		for(final NecessarilyListenerManagers an : ClassUtil.getAllAnnotations(clazz, NecessarilyListenerManagers.class))
			for(final Class<? extends ListenerManager> cl : an.value()) if(!this.listenerManagers.containsClass(cl)) {
				Constructor<? extends ListenerManager> constructor = null;
				try {
					constructor = cl.getConstructor();
				}catch(NoSuchMethodException | SecurityException e) {
					Log.warn(e);
				}
				ListenerManager lm = null;
				try {
					lm = constructor.newInstance();
				}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					Log.warn(e);
				}
				this.addListenerManager(lm);
			}
	}
	
	public void update() {
		synchronized(this.eventQuery) {
			while(!this.eventQuery.isEmpty()) {
				final Event event = this.eventQuery.remove();
				for(final ListenerManager l : this.listenerManagers) l.event(event);
				this.deleteEvent(event);
			}
		}
	}

	public void clear() {
		listenerManagers.clear();
		eventQuery.clear();
	}
}
