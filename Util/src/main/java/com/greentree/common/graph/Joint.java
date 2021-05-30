package com.greentree.common.graph;

import java.util.Objects;

public class Joint<E> {

	private final E a, b;

	public Joint(E a, E b) {
		if(a.equals(b))throw new IllegalArgumentException("Joint a==b ("+a+")");
		this.a = Objects.requireNonNull(a);
		this.b = Objects.requireNonNull(b);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Joint other = (Joint) obj;
		if(a.equals(other.a) && b.equals(other.b))return true;
		if(b.equals(other.a) && a.equals(other.b))return true;
		return false;
	}

	public E getA() {
		return a;
	}

	public E getB() {
		return b;
	}

	@Override
	public String toString() {
		return "(" + a + " " + b + ")";
	}

}
