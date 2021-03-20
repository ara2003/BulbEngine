package com.greentree.collection;

import java.io.Serializable;
import java.util.Set;

/** @author Arseny Latyshev */
public interface ClassTree<E> extends Set<E>, Serializable {
	
	public boolean containsClass(Class<? extends E> clazz);
	public <T> Set<T> get(Class<T> c);
	
	
}
