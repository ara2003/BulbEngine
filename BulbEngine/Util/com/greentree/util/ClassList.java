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
	
	private final Map<Class<?>, List<E>> map;
	
	
	public ClassList(){
		map = new HashMap<>();
	}
	
	
	private void add(Class<?> clazz, E e) {
		get(clazz).add(e);
		if(clazz.getSuperclass() != null)add(clazz.getSuperclass(), e);
	}
	
	
	public void add(E e) {
		add(e.getClass(), e);
	}
	
	public void addAll(Class<?> c, List<E> list) {
		List<E> list0 = remove(c);
		if(list0 == null) {
			addAll(list);
		}else {
			list0.addAll(list);
			addAll(list0);
		}
	}
	
	public void addAll(List<E> set) {
		for(E e : set)add(e);
	}
	
	public boolean contains(E obj) {
		return get(Object.class).contains(obj);
	}
	
	public List<E> get(Class<?> c) {
		List<E> list = map.get(Objects.requireNonNull(c));
		if(list == null) {
			map.put(c, new ArrayList<>());
			return get(c);
		}
		return list;
	}
	
	
	@Override
	public Iterator<E> iterator() {
		return get(Object.class).iterator();
	}
	
	public List<E> remove(Class<?> c) {
		List<E> list = map.remove(Objects.requireNonNull(c));
		remove(c, c);
		return list;
	}
	
	private void remove(Class<?> node, Class<?> clazz) {
		if(node == null)return;
		List<E> list = get(node);
		list.removeIf(c->c.getClass().equals(clazz));
		remove(node.getSuperclass(), clazz);
	}
	
	public boolean remove(E obj) {
		return remove(obj, obj.getClass());
	}
	
	private boolean remove(E obj, Class<?> clazz) {
		List<E> list = get(clazz);
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

	public boolean containsClass(Class<?> clazz) {
		return map.containsKey(clazz);
	}
	
}
