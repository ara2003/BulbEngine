package com.greentree.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** @author Arseny Latyshev */
public class ClassList<E> implements Collection<E> {
	
	private final Map<Class<?>, Set<?>> map;
	
	public ClassList() {
		map = new HashMap<>();
	}
	
	public ClassList(Collection<? extends E> collection) {
		this();
		addAll(collection);
	}
	
	@SuppressWarnings("unchecked")
	private void add(Class<?> clazz, Object e) {
		if(clazz == null) return;
		((Set<Object>)get(clazz)).add(e);
		add(clazz.getSuperclass(), e);
		for(Class<?> interfase : clazz.getInterfaces())add(interfase, e);
	}
	
	@Override
	public boolean add(E object) {
		add(object.getClass(), object);
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean t = false;
		for(E e : collection) {
			if(add(e)) t = true;
		}
		return t;
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public boolean contains(Object obj) {
		return get(Object.class).contains(obj);
	}
	
	@Override
	public boolean containsAll(Collection<?> collection) {
		for(Object obj : collection) if(!contains(obj)) return false;
		return true;
	}
	
	public boolean containsClass(Class<?> clazz) {
		return map.containsKey(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Set<? extends T> get(Class<T> c) {
		if(c == null) return (Set<T>) get(Object.class);
		Set<T> set = (Set<T>) map.get(c);
		if(set == null) {
			map.put(c, new HashSet<>());
			return get(c);
		}
		return set;
	}
	
	@Override
	public boolean isEmpty() {
		return get(Object.class).isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> iterator() {
		return ((Set<E>) get(Object.class)).iterator();
	}
	
	@Override
	public boolean remove(Object obj) {
		boolean res = contains(obj);
		remove(obj.getClass(), obj);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private void remove(Class<?> clazz, Object obj) {
		if(clazz == null)return;
		((Set<Object>)get(clazz)).remove(obj);
		remove(clazz.getSuperclass(), obj);
		for(Class<?> interfase : clazz.getInterfaces())remove(interfase, obj);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean removeAll(Collection<?> collection) {
		return removeAll0((Collection<? extends E>) collection);
	}
	
	private boolean removeAll0(Collection<? extends E> collection) {
		collection.forEach(e->remove(e));
		boolean t = false;
		for(E e : collection) {
			if(add(e)) t = true;
		}
		return t;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}
	
	@Override
	public int size() {
		return get(Object.class).size();
	}
	
	@Override
	public Object[] toArray() {
		return map.values().toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}
	
	@Override
	public String toString() {
		return "ClassList " + get(Object.class);
	}
}
