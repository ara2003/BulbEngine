package com.greentree.loading;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class LoaderUtil {
	
	public static List<Field> getAllFields(final Class<?> clazz) {
		final List<Field> list = new ArrayList<>();
		for(final Class<?> c : getAllPerant(clazz)) for(final Field f : c.getDeclaredFields()) list.add(f);
		return list;
	}
	
	public static List<Class<?>> getAllPerant(Class<?> clazz) {
		final List<Class<?>> list = new ArrayList<>();
		while(!clazz.equals(Object.class) && clazz != null) {
			list.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	public static <A extends Annotation> A getAnnotationofPeraent(final Class<?> clazz, final Class<A> annotation) {
		for(final Class<?> c : getAllPerant(clazz)) {
			final A annotation1 = c.getAnnotation(annotation);
			if(annotation1 != null) return annotation1;
		}
		return null;
	}

//	public static <T> T clone(T t) {
//		Class<T> clazz = (Class<T>) t.getClass();
//		clazz.getConstructor();
//	}
}
