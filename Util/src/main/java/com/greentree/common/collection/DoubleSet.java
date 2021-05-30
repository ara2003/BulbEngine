package com.greentree.common.collection;

import java.util.ArrayList;

import com.greentree.common.ObjectPool;
import com.greentree.common.pair.UnOrentetPair;

/** @author Arseny Latyshev */
public class DoubleSet<E> extends ArrayList<UnOrentetPair<E>> {

	private static final long serialVersionUID = 1L;
	private final UnOrentetPairPool<E> pool;

	public DoubleSet() {
		this.pool = new UnOrentetPairPool<>();
	}

	public void addPair(final E a, final E b) {
		UnOrentetPair<E> e = this.pool.get(a, b);
		if(contains(e))return;
		add(e);
	}

	public boolean contains(final E a, final E b) {
		final UnOrentetPair<E> el  = this.pool.get(a, b);
		final boolean          res = this.contains(el);
		this.pool.add(el);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof DoubleSet)) return false;
		DoubleSet<E> other = (DoubleSet<E>) obj;
		if(other.size() != size())return false;
		for(UnOrentetPair<E> a : this) if(!other.contains(a))return false;
		return true;
	}

	public boolean remove(final E a, final E b) {
		final UnOrentetPair<E> el  = this.pool.get(a, b);
		final boolean          res = this.remove(el);
		this.pool.add(el);
		return res;
	}
}

class UnOrentetPairPool<T> extends ObjectPool<UnOrentetPair<T>> {

	protected UnOrentetPair<T> get(T a, T b) {
		return new UnOrentetPair<>(a, b);
	}
}
