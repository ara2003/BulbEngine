package com.greentree.event;

import java.util.Arrays;
import java.util.Collection;

import com.greentree.common.ClassUtil;

/** @author Arseny Latyshev */
public abstract class MutiListenerListenerManager<E extends Event> extends ListenerManagerWithListener<E, Listener> {
	
	private static final long serialVersionUID = 1L;
	private final Collection<Class<? extends Listener>> listenerClasses;
	
	@SafeVarargs
	public MutiListenerListenerManager(final Class<? extends Listener>... clazz) {
		this.listenerClasses = Arrays.asList(clazz);
		
		for(final Class<? extends Listener> cl : clazz) {
			Collection<NecessarilyListenerManagers> collection = ClassUtil.getAllAnnotations(cl, NecessarilyListenerManagers.class);
			boolean containsClass = false;
			for(final NecessarilyListenerManagers n : collection) {
				if(Arrays.asList(n.value()).contains(this.getClass())){
					containsClass = true;
					break;
				}
			}
			if(!containsClass)throw new RuntimeException(getClass() + "sork with and " + cl + " not has " + NecessarilyListenerManagers.class + " with him");
		}
	}
	
	@Override
	protected boolean isUse(final Listener listener) {
		return this.listenerClasses.parallelStream().anyMatch(e->e.isInstance(listener));
	}
}
