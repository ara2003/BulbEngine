package com.greentree.common.collection;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/** @author Arseny Latyshev */
public class HashMapClassTree<E> extends AbstractSet<E> implements ClassTree<E> {
	
	private static final long serialVersionUID = 1L;
	private final Map<Class<?>, Set<?>> map;
	
	public HashMapClassTree() {
		this.map = new HashMap<>();
	}
	
	public HashMapClassTree(final HashMapClassTree<? extends E> classTree) {
		this.map = new HashMap<>(classTree.map);
	}
	
	public HashMapClassTree(final Iterable<E> collection) {
		this();
		this.addAll(collection);
	}
	
	@SuppressWarnings("unchecked")
	private void add(final Class<?> clazz, final Object e) {
		if(clazz == null) return;
		((Set<Object>) this.get(clazz)).add(e);
		this.add(clazz.getSuperclass(), e);
		for(final Class<?> interfase : clazz.getInterfaces()) this.add(interfase, e);
	}
	
	public boolean add(final E object) {
		this.add(object.getClass(), object);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean addAll(final Iterable<?> iterable) {
		if(iterable == null) throw new NullPointerException("iterable is null");
		boolean t = false;
		for(final Object e : iterable) try {
			if(this.add((E) e)) t = true;
		}catch(final Exception ex) {
			throw new IllegalArgumentException("iterable contains illegal class " + e, ex);
		}
		return t;
	}
	
	public void clear() {
		this.map.clear();
	}
	
	public boolean contains(final Object obj) {
		return this.get(Object.class).contains(obj);
	}
	
	public boolean containsAll(final Collection<?> collection) {
		for(final Object obj : collection) if(!this.contains(obj)) return false;
		return true;
	}
	
	public boolean containsClass(final Class<? extends E> clazz) {
		return this.map.containsKey(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Set<T> get(final Class<T> c) {
		if(c == null) throw new NullPointerException("class is null");
		final Set<T> set = (Set<T>) this.map.get(c);
		if(set == null) {
			this.map.put(c, new HashSet<>());
			return this.get(c);
		}
		return set;
	}
	
	public boolean isEmpty() {
		return this.get(Object.class).isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> iterator() {
		return (Iterator<E>) this.get(Object.class).iterator();
	}
	
	@SuppressWarnings("unchecked")
	private void remove(final Class<?> clazz, final Object obj) {
		if(clazz == null) return;
		((Set<Object>) this.get(clazz)).remove(obj);
		this.remove(clazz.getSuperclass(), obj);
		for(final Class<?> interfase : clazz.getInterfaces()) this.remove(interfase, obj);
	}
	
	public boolean remove(final Object obj) {
		final boolean res = this.contains(obj);
		this.remove(obj.getClass(), obj);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public boolean removeAll(final Collection<?> collection) {
		return this.removeAll0((Collection<? extends E>) collection);
	}
	
	private boolean removeAll0(final Collection<? extends E> collection) {
		boolean t = false;
		for(final E e : collection) if(this.remove(e)) t = true;
		return t;
	}
	
	public int size() {
		return this.get(Object.class).size();
	}
	
	@Override
	public String toString() {
		return "ClassList " + this.get(Object.class);
	}
}
