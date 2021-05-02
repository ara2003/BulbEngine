package com.greentree.common.collection;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @author Arseny Latyshev */
public class OneClassSet<E> extends AbstractSet<E> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Map<Class<?>, E> map;
	
	public OneClassSet() {
		map = new ConcurrentHashMap<>();
	}
	
	public boolean containsClass(Class<?> o) {
		return map.containsKey(o);
	}
	
	public OneClassSet(final int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	public OneClassSet(final Map<Class<?>, E> m) {
		map = new HashMap<>(m);
	}
	
	public OneClassSet(Collection<? extends E> collection) {
		this(collection.size());
		addAll(collection);
	}
	
	public OneClassSet(OneClassSet<E> cs) {
		map = new HashMap<>(cs.map);
	}
	
	@Override
	public boolean add(E e) {
		if(e == null)return false;
		if(!containsClass(e.getClass())) {
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
