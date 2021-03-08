package com.greentree.util;

import java.util.function.Function;

/**
 * @author Arseny Latyshev
 *
 */
public final class Util {
	
	private Util() {
	}
	
	/**
	 * @return element in collection with min cast
	 */
	public static <E> E minElement(Iterable<E> collection, Function<E, Float> function) {
		float min = Float.MAX_VALUE;
		E e = null;
		for(E a : collection) {
			float c = function.apply(a);
			if(min > c){
				min = c;
				e = a;
			}
		}
		return e;
	}

	public static <E> float min(Iterable<E> collection, Function<E, Float> function) {
		float min = Float.MAX_VALUE;
		for(E a : collection) {
			float c = function.apply(a);
			if(min > c){
				min = c;
			}
		}
		return min;
	}
	
}
