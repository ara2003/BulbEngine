package com.greentree.action;

/** @author Arseny Latyshev */
public abstract class AbstractEventAction<E, L> extends Action<L> {
	
	public final void action(E e){
		action(l -> {
			action(l, e);
		});
	}
	
	protected abstract void action(L l, E e);
	
}
