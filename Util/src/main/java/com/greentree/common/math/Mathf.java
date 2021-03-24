package com.greentree.common.math;

import java.util.function.Function;

/**
 * @author Arseny Latyshev
 *
 */
public final class Mathf {

	private Mathf() {
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
	public static float lerp(float a, float b, float k) {
		return (b - a)*k + a;
	}
	
	public static float lerp(float a, float b, double k) {
		return lerp(a, b, (float)k);
	}
	
	
	
}
