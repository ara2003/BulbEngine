package com.greentree.util;

public class Pair<T1, T2> {

	public T1 first;
	public T2 second;
	
	public Pair() {
	}

	public Pair(final T1 first, final T2 second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString() {
		return "{" + first + ", " + second + "}";
	}

	
}
