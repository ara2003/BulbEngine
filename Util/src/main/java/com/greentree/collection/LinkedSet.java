package com.greentree.collection;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/** @author Arseny Latyshev */
@Deprecated
public class LinkedSet<E> extends AbstractSet<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Map<E, LinkedElement<E>> map = new HashMap<>();
	private LinkedElement<E> placeholder = new LinkedElement<>();
	private LinkedElement<E> head = this.placeholder;
	
	@Override
	public boolean add(final E e) {
		LinkedElement<E> element = this.map.putIfAbsent(e, this.placeholder);
		if(element != null) return false;
		element               = this.placeholder;
		element.exists        = true;
		element.value         = e;
		this.placeholder      = new LinkedElement<>();
		this.placeholder.prev = element;
		element.next          = this.placeholder;
		return true;
	}
	
	@Override
	public boolean contains(final Object o) {
		return this.map.containsKey(o);
	}
	
	@Override
	public boolean isEmpty() {
		return this.head == this.placeholder;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
	@Override
	public boolean remove(final Object o) {
		final LinkedElement<E> removedElement = this.map.remove(o);
		if(removedElement == null) return false;
		this.removeElementFromLinkedList(removedElement);
		return true;
	}
	
	private void removeElementFromLinkedList(final LinkedElement<E> element) {
		element.exists    = false;
		element.value     = null;
		element.next.prev = element.prev;
		if(element.prev != null) {
			element.prev.next = element.next;
			element.prev      = null;
		}else this.head = element.next;
	}
	
	@Override
	public int size() {
		return this.map.size();
	}
	
	private class ElementIterator implements Iterator<E> {
		
		LinkedElement<E> next = LinkedSet.this.head;
		LinkedElement<E> current = null;
		
		LinkedElement<E> findNext() {
			LinkedElement<E> n = this.next;
			while(!n.exists && n.next != null) this.next = n = n.next;
			return n;
		}
		
		@Override
		public boolean hasNext() {
			return this.findNext().exists;
		}
		
		@Override
		public E next() {
			final LinkedElement<E> n = this.findNext();
			if(!n.exists) throw new NoSuchElementException();
			this.current = n;
			this.next    = n.next;
			return n.value;
		}
		
		@Override
		public void remove() {
			if(this.current == null) throw new IllegalStateException();
			if(LinkedSet.this.map.remove(this.current.value, this.current))
				LinkedSet.this.removeElementFromLinkedList(this.current);
			else throw new NoSuchElementException();
		}
	}
	
	private static class LinkedElement<E> {
		
		E value;
		boolean exists;
		LinkedElement<E> prev;
		LinkedElement<E> next;
	}
}
