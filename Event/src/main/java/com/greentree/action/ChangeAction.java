package com.greentree.action;

import java.util.function.Consumer;

/** 
 * @author Arseny Latyshev
 * @deprecated use EventAction
 *  */
@Deprecated
public class ChangeAction<T> extends Action<Consumer<T>> {
	
	public void action(final T t) {
		this.action(c -> {
			c.accept(t);
		});
	}
}
