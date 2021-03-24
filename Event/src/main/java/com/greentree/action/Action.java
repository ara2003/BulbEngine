package com.greentree.action;

import java.util.ArrayList;
import java.util.Collection;

/** @author Arseny Latyshev */
public abstract class Action<E, L> {
	
	protected Collection<L> listeners;
	
	public Action() {
		listeners = new ArrayList<>();
	}
	
	public final void addListener(L l) {
		listeners.add(l);
	}
	
	public final void action(E e){
		for(L l : listeners) {
			event(l, e);
		}
	}
	
	protected abstract void event(L l, E e);
	
}
