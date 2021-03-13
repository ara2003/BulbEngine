package com.greentree.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** @author Arseny Latyshev */
public class ClassList<E> implements Collection<E> {
	
	private final Map<Class<?>, List<?>> map;
	
	public ClassList() {
		map = new HashMap<>();
	}
	
	public ClassList(Collection<? extends E> collection) {
		this();
		addAll(collection);
	}
	
	@SuppressWarnings("unchecked")
	private boolean add(Class<?> clazz, E e) {
		if(clazz == null) return false;
		List<Object> list = (List<Object>) get(clazz);
		list.add(e);
		return add(clazz.getSuperclass(), e);
	}
	
	@Override
	public boolean add(E object) {
		return add(object.getClass(), object);
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
	
	@SuppressWarnings({"hiding","unchecked"})
	public <E> List<E> get(Class<E> c) {
		if(c == null) return (List<E>) get(Object.class);
		List<E> list = (List<E>) map.get(c);
		if(list == null) {
			map.put(c, new ArrayList<>());
			return get(c);
		}
		return list;
	}
	
	@Override
	public boolean isEmpty() {
		return get(Object.class).isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> iterator() {
		return ((List<E>) get(Object.class)).iterator();
	}
	
	public List<?> remove(Class<?> c) {
		List<?> list = map.remove(Objects.requireNonNull(c));
		remove(c, c);
		return list;
	}
	
	private void remove(Class<?> node, Class<?> clazz) {
		if(node == null) return;
		List<?> list = get(node);
		list.removeIf(c->c.getClass().equals(clazz));
		remove(node.getSuperclass(), clazz);
	}
	
	@Override
	public boolean remove(Object obj) {
		return remove(obj, obj.getClass());
	}
	
	private boolean remove(Object obj, Class<?> clazz) {
		List<?> list = get(clazz);
		if(!list.contains(obj)) return false;
		list.remove(obj);
		if(clazz.getSuperclass() != null) remove(obj, clazz.getSuperclass());
		return true;
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
