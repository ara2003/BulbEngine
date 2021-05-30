package com.greentree.common.math;

import java.util.function.Function;

import org.joml.Vector2f;

/**
 * @author Arseny Latyshev
 *
 */
public final class Mathf {

	private Mathf() {
	}

	public static int get2Fold(final float fold) {
		int ret = 2;
		while(ret < fold) ret *= 2;
		return ret;
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

	public static Float distanse(Vector2f a, Vector2f b) {
		return (float) Math.sqrt(distanseSqr(a, b));
	}
	
	public static Float distanseSqr(Vector2f a, Vector2f b) {
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		return (dx*dx)+(dy*dy);
	}

	public static float cos(double d) {
		return (float)Math.cos(d);
	}
	public static float sin(double d) {
		return (float)Math.sin(d);
	}

	public static float sqrt(float d) {
		return (float)Math.sqrt(d);
	}
	
	
	
}
