package com.greentree.common.graph;

import java.util.ArrayList;
import java.util.List;

public class Cycle<E> extends ArrayList<Joint<E>> {

	private static final long serialVersionUID = 1L;

	public Cycle(List<E> cycle) {
		for(int i = 0; i < cycle.size()-1; i++) add(new Joint<>(cycle.get(i), cycle.get(i+1)));
		add(new Joint<>(cycle.get(cycle.size()-1), cycle.get(0)));
		//		add(new Joint<>(cycle.get(0), cycle.get(cycle.size()-1)));
	}

	@Override
	public boolean add(Joint<E> arg0) {
		//		if(contains(arg0))return false;
		return super.add(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object obj) {
		if(!(obj instanceof Joint))return false;
		Joint<E> other = (Joint<E>) obj;
		for(Joint<E> a : this)if(other.equals(a))return true;
		return false;
	}

	public List<Joint<E>> cross(Cycle<E> cycle){
		List<Joint<E>> res = new ArrayList<>();
		for(Joint<E> v : cycle)if(contains(v))res.add(v);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Cycle<E> other = (Cycle<E>) obj;
		for(Joint<E> a : this)if(!other.contains(a))return false;
		for(Joint<E> a : other)if(!this.contains(a))return false;
		return true;
	}

}

