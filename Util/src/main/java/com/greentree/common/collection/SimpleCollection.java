package com.greentree.common.collection;

import java.io.Serializable;

/**
 * @author Arseny Latyshev
 *
 */
public interface SimpleCollection<E> extends Serializable, Iterable<E> {
	
	boolean add(E e);
	void clear();
	boolean isEmpty();
	default boolean contains(E e){
		for(E el : this)if(e.equals(el))return true;
		return false;
	}
	default boolean addAll(Iterable<? extends E> iterable){
		boolean res = false;
		for(E e : iterable)if(add(e))res = true;
		return res;
	}
	default boolean containsAll(Iterable<? extends E> iterable){
		for(E e : iterable)if(!contains(e))return false;
		return true;
	}
	
}
