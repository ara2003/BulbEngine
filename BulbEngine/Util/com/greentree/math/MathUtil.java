package com.greentree.math;

import java.util.Collection;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

/**
 * @author Arseny Latyshev
 *
 */
/**
 * @author Arseny Latyshev
 *
 */
@UtilityClass
public class MathUtil {
	
	/**
	 * @return element in collection with min cast
	 */
	public static <E> E minElement(Collection<E> collection, Function<E, Float> function) {
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
	
	@FunctionalInterface
	public interface Cast<T> {
		float cast(T e);
	}
	
}
