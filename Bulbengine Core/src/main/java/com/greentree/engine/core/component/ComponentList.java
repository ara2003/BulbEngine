package com.greentree.engine.core.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.GameObject;

/** @author Arseny Latyshev */
public class ComponentList<E extends GameComponent> implements List<E> {
	
	private final List<E> list;
	
	public ComponentList() {
		this.list = new ArrayList<>();
	}
	
	public ComponentList(final Collection<E> list) {
		this.list = new ArrayList<>(list);
	}
	
	public ComponentList(final int n) {
		this.list = new ArrayList<>(n);
	}
	
	@Override
	public boolean add(final E e) {
		return this.list.add(e);
	}
	
	@Override
	public void add(final int index, final E element) {
		this.list.add(index, element);
	}
	
	@Override
	public boolean addAll(final Collection<? extends E> c) {
		return this.list.addAll(c);
	}
	
	@Override
	public boolean addAll(final int index, final Collection<? extends E> c) {
		return this.list.addAll(index, c);
	}
	
	@Override
	public void clear() {
		this.list.clear();
	}
	
	@Override
	public boolean contains(final Object o) {
		return this.list.contains(o);
	}
	
	@Override
	public boolean containsAll(final Collection<?> c) {
		return this.list.containsAll(c);
	}
	
	@Override
	public E get(final int index) {
		return this.list.get(index);
	}
	
	public List<? extends GameComponent> getList() {
		return this.list;
	}
	
	public Collection<GameObject> getObjects() {
		final Collection<GameObject> listObjects = new ArrayList<>();
		for(final E e : this.list) listObjects.add(e.getObject());
		return listObjects;
	}
	
	@Override
	public int indexOf(final Object o) {
		return this.list.indexOf(o);
	}
	
	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}
	
	@Override
	public Iterator<E> iterator() {
		return this.list.iterator();
	}
	
	@Override
	public int lastIndexOf(final Object o) {
		return this.list.lastIndexOf(o);
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return this.list.listIterator();
	}
	
	@Override
	public ListIterator<E> listIterator(final int index) {
		return this.list.listIterator(index);
	}
	
	@Override
	public E remove(final int index) {
		return this.list.remove(index);
	}
	
	@Override
	public boolean remove(final Object o) {
		return this.list.remove(o);
	}
	
	@Override
	public boolean removeAll(final Collection<?> c) {
		return this.list.removeAll(c);
	}
	
	@Override
	public boolean retainAll(final Collection<?> c) {
		return this.list.retainAll(c);
	}
	
	@Override
	public E set(final int index, final E element) {
		return this.list.set(index, element);
	}
	
	@Override
	public int size() {
		return this.list.size();
	}
	
	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}
	
	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}
	
	@Override
	public <T> T[] toArray(final T[] a) {
		return this.list.toArray(a);
	}
	
	@Override
	public String toString() {
		return this.list.toString();
	}
}
