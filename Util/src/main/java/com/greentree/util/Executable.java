package com.greentree.util;

/** @author Arseny Latyshev */
@FunctionalInterface
public interface Executable<T> {
	
	default Executable<T> and(final Executable<T> other) {
		return ()-> {
			this.execute();
			other.execute();
		};
	}
	
	void execute();
}
