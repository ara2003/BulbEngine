package com.greentree.collection;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Arseny Latyshev
 *
 */
public class ConurentClassTree<E> extends AbstractSet<E> implements ClassTree<E> {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean containsClass(final Class<? extends E> clazz) {
		return false;
	}

	@Override
	public <T> Set<T> get(Class<T> c) {
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}
}
