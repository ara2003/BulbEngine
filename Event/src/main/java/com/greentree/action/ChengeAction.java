package com.greentree.action;

import java.util.function.Consumer;

/**
 * @author Arseny Latyshev
 *
 */
public class ChengeAction<T> extends Action<T, Consumer<T>> {

	@Override
	protected void event(Consumer<T> c, T e) {
		c.accept(e);
	}
	
	
	
}
