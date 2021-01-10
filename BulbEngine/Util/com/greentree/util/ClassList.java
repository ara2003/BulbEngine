package com.greentree.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Arseny Latyshev
 *
 */
public class ClassList<E> implements Iterable<E> {
	
	private final Map<Class<?>, List<?>> map;
	
	public ClassList(){
		map = new HashMap<>();
	}
	
	@SuppressWarnings("unchecked")
	private void add(Class<?> clazz, E e) {
		if(clazz == null)return;
	    List<Object> list = (List<Object>) get(clazz);
	    list.add(e);
		add(clazz.getSuperclass(), e);
	}
	
	public void add(E object) {
		add(object.getClass(), object);
	}
	
	public void addAll(List<? extends E> list) {
		for(E e : list)add(e);
	}
	
	public boolean contains(Object obj) {
		return get(Object.class).contains(obj);
	}
	
	public boolean containsClass(Class<?> clazz) {
		return map.containsKey(clazz);
	}
	
	
	@SuppressWarnings({"hiding","unchecked"})
	public <E> List<E> get(Class<E> c) {
		List<E> list = (List<E>) map.get(Objects.requireNonNull(c));
		if(list == null) {
			map.put(c, new ArrayList<>());
			return get(c);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> iterator() {
		return ((List<E>)get(Object.class)).iterator();
	}
	
	public List<?> remove(Class<?> c) {
		List<?> list = map.remove(Objects.requireNonNull(c));
		remove(c, c);
		return list;
	}
	
	private void remove(Class<?> node, Class<?> clazz) {
		if(node == null)return;
		List<?> list = get(node);
		list.removeIf(c->c.getClass().equals(clazz));
		remove(node.getSuperclass(), clazz);
	}
	
	public boolean remove(Object obj) {
		return remove(obj, obj.getClass());
	}
	
	private boolean remove(Object obj, Class<?> clazz) {
		List<?> list = get(clazz);
		if(!list.contains(obj))return false;
		list.remove(obj);
		if(clazz.getSuperclass() != null)remove(obj, clazz.getSuperclass());
		return true;
	}
	
	public int size() {
		return get(Object.class).size();
	}
	
	@Override
	public String toString() {
		return "ClassList [map=" + map + "]";
	}
	
}
