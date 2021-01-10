package com.greentree.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Arseny Latyshev
 *
 */
public final class ArrayUtil {

	public static <E> List<E> findObjects(Iterable<E> list, final Predicate<E> filter) {
		final List<E> list0 = new ArrayList<>();
		for(E e : list)if(filter.test(e))list0.add(e);
		return list0;
	}
	
}
