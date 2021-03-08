package com.greentree.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** @author Arseny Latyshev */
public class OneClassSet<E> extends AbstractSet<E> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Map<Class<?>, E> map;
	
	public OneClassSet() {
		map = new HashMap<>();
	}
	
	public OneClassSet(final int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	public OneClassSet(final Map<Class<?>, E> m) {
		map = new HashMap<>(m);
	}
	public OneClassSet(Collection<E> collection) {
		this(collection.size());
		addAll(collection);
	}
	
	public OneClassSet(OneClassSet<E> cs) {
		map = new HashMap<>(cs.map);
	}
	
	@Override
	public boolean add(E e) {
		if(map.get(e.getClass()) == null) {
			map.put(e.getClass(), e);
			return true;
		}
		return false;
	}
	
	@Override
	public OneClassSet<E> clone() {
		return new OneClassSet<>(this);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends E> T get(Class<T> clazz) {
		return (T) map.get(clazz);
	}
	
	@Override
	public Iterator<E> iterator() {
		return map.values().iterator();
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
}
