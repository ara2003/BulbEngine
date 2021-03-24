package com.greentree.common.pair;

import java.util.Objects;

public class Pair<T1, T2> {
	
	public T1 first;
	public T2 second;
	
	public Pair() {
	}
	
	public Pair(final T1 first, final T2 second) {
		this.first = Objects.requireNonNull(first);
		this.second = Objects.requireNonNull(second);
	}
	
	@Override
	public String toString() {
		return "{" + first + ", " + second + "}";
	}

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = 1;
		result = prime * result + (first.hashCode());
		result = prime * result + (second.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Pair)) return false;
		Pair other = (Pair) obj;
		if(!first.equals(other.first)) return false;
		if(!second.equals(other.second)) return false;
		return true;
	}

}
