package com.greentree.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Arseny Latyshev
 *
 */
public class Action<L> {
	
	private Collection<L> listeners;
	
	public Action() {
		listeners = new ArrayList<>();
	}
	
	public final boolean addListener(L l) {
		return listeners.add(l);
	}
	
	public void action(Consumer<L> consumer){
		for(L l : listeners) {
			consumer.accept(l);
		}
	}
	
}
