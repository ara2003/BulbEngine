package com.greentree.action;

import java.util.function.Consumer;

/**
 * @author Arseny Latyshev
 */
public class EventAction<T> extends AbstractEventAction<T, Consumer<T>> {
	
	@Override
	protected void action(Consumer<T> c, T e) {
		c.accept(e);
	}
	
}
