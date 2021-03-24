package com.greentree.common.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Arseny Latyshev
 *
 */
public final class ArrayUtil {

	private ArrayUtil() {
	}
	
	public static <E> List<E> findObjects(Iterable<E> list, Predicate<E> filter) {
		if(list == null)return null;
		if(filter == null)filter = obj->false;
		final List<E> list0 = new ArrayList<>();
		for(E e : list)if(filter.test(e))list0.add(e);
		return list0;
	}
	
}
