package com.greentree.common.collection;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/** @author Arseny Latyshev */
public class HashMapClassTree<E> implements ClassTree<E> {
	
	private static final long serialVersionUID = 1L;
	private final ConcurrentHashMap<Class<?>, Queue<?>> map;
	
	public HashMapClassTree() {
		this.map = new ConcurrentHashMap<>();
	}
	
	public HashMapClassTree(final HashMapClassTree<? extends E> classTree) {
		this.map = new ConcurrentHashMap<>(classTree.map);
	}
	
	public HashMapClassTree(final Iterable<E> collection) {
		this();
		this.addAll(collection);
	}
	
	@SuppressWarnings("unchecked")
	private void add(final Class<?> clazz, final Object e) {
		if(clazz == null) throw new NullPointerException("clazz is null");
		((Queue<Object>) this.get(clazz)).add(e);
		final Class<?> superClazz = clazz.getSuperclass();
		if(superClazz != null) this.add(superClazz, e);
		for(final Class<?> interfase : clazz.getInterfaces()) this.add(interfase, e);
	}
	
	@Override
	public boolean add(final E object) {
		if(object == null) throw new NullPointerException("object is null");
		this.add(object.getClass(), object);
		return true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean addAll(final Iterable<E> iterable) {
		if(iterable == null) throw new NullPointerException("iterable is null");
		boolean t = false;
		for(final Object e : iterable) try {
			if(this.add((E) e)) t = true;
		}catch(final Exception ex) {
			throw new IllegalArgumentException("iterable contains illegal class " + e, ex);
		}
		return t;
	}
	
	@Override
	public void clear() {
		this.map.clear();
	}
	
	@Override
	public boolean contains(final Object obj) {
		return this.get(Object.class).contains(obj);
	}
	
	public boolean containsAll(final Collection<?> collection) {
		for(final Object obj : collection) if(!this.contains(obj)) return false;
		return true;
	}
	
	@Override
	public boolean containsClass(final Class<? extends E> clazz) {
		return this.map.containsKey(clazz);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> Queue<T> get(final Class<T> c) {
		if(c == null) throw new NullPointerException("class is null");
		final Queue<T> set = (Queue<T>) this.map.get(c);
		if(set == null) {
			this.map.put(c, new ArrayDeque<>());
			return this.get(c);
		}
		return set;
	}
	
	@Override
	public <T> T getOne(final Class<T> c) {
		return this.get(c).element();
	}
	
	@Override
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
		((Queue<Object>) this.get(clazz)).remove(obj);
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
	
	@Override
	public <T> T removeOne(final Class<T> c) {
		final T a = this.getOne(c);
		this.remove(a);
		return a;
	}
	
	public int size() {
		return this.get(Object.class).size();
	}
	
	@Override
	public String toString() {
		return "ClassList " + this.get(Object.class);
	}
}
