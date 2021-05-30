package com.greentree.common;

import java.util.Objects;

/** @author Arseny Latyshev */
public class Optional<T> {

	private final T value;

	public Optional(final T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public Optional<T> notNull() {
		Objects.requireNonNull(value);
		return this;
	}

}
