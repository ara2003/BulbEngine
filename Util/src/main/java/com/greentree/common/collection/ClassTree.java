package com.greentree.common.collection;

import java.io.Serializable;
import java.util.Queue;

/** @author Arseny Latyshev */
public interface ClassTree<E> extends Iterable<E>, Serializable {
	
	boolean add(E e);
	
	default boolean addAll(final Iterable<E> components) {
		boolean res = false;
		for(final E e : components) if(this.add(e)) res = true;
		return res;
	}
	
	void clear();
	boolean contains(E component);
	boolean containsClass(Class<? extends E> clazz);
	<T> Queue<T> get(Class<T> c);
	<T> T getOne(Class<T> c);
	boolean isEmpty();
	<T> T removeOne(Class<T> c);
}
