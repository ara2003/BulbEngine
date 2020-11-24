package com.greentree.engine.loading;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class LoaderUtil {

	public static List<Field> getAllFields(Class<?> clazz) {
		final List<Field> list = new ArrayList<>();
		while(!clazz.equals(Object.class) && clazz != null) {
			for(final Field f : clazz.getDeclaredFields()) list.add(f);
			clazz = clazz.getSuperclass();
		}
		return list;
	}
}
