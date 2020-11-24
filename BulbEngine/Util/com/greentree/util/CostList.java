package com.greentree.util;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

class CostElement<A> {

	float cost;
	A element;
	
	public CostElement(final A a, final float f) {
		element = a;
		cost = f;
	}

	public float getCost() {
		return cost;
	}

	public A getElement() {
		return element;
	}
}

public class CostList<A> extends AbstractList<A> implements Serializable {

	private static final long serialVersionUID = 1L;
	private final List<CostElement<A>> list = new ArrayList<>();
	
	@Override
	public boolean add(final A a) {
		return add(a, 1f);
	}
	
	public boolean add(final A a, final float f) {
		return list.add(new CostElement<>(a, f));
	}
	
	public A get() {
		float n = 0f;
		for(final CostElement<A> a : list) n += a.cost;
		n = (float) (n * Math.random());
		for(final CostElement<A> a : list) {
			n -= a.cost;
			if(n <= 0f) return a.element;
		}
		return get(0);
	}

	@Override
	public A get(final int i) {
		return list.get(i).element;
	}
	
	public float getAllCost() {
		float sum = 0f;
		for(final CostElement<A> a : list) sum += a.cost;
		return sum;
	}
	
	@Override
	public int size() {
		return list.size();
	}
}
