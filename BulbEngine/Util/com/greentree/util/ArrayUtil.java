package com.greentree.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.experimental.UtilityClass;

/**
 * @author Arseny Latyshev
 *
 */
@UtilityClass
public final class ArrayUtil {

	public static <E> List<E> findObjects(Iterable<E> list, Predicate<E> filter) {
		if(list == null)return null;
		if(filter == null)filter = obj->false;
		final List<E> list0 = new ArrayList<>();
		for(E e : list)if(filter.test(e))list0.add(e);
		return list0;
	}
	
}
