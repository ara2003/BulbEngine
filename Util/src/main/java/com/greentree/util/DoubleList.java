package com.greentree.util;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Arseny Latyshev
 *
 */
public class DoubleList<E1, E2> {
	
	List<Pair<E1, E2>> list;
	
	public DoubleList() {
		this(10);
	}
	
	public DoubleList(int initialCapacity) {
		list = new ArrayList<>();
	}
	
	public void add(E1 e1, E2 e2){
		var pair = new Pair<>(e1, e2);
		list.add(pair);
	}
	
	public int indexOf(E1 a, E2 b) {
		return list.indexOf(new Pair<>(a, b));
	}
	
	public boolean remove(E1 a, E2 b) {
		int index = indexOf(a, b);
		if(index == -1)return false;
		list.remove(index);
		return true;
	}
	
}
