package com.greentree.serialize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class ClassUtil {
	
	public static List<Field> getAllFields(final Class<?> clazz) {
		final List<Field> list = new ArrayList<>();
		for(final Class<?> c : getAllPerant(clazz)) for(final Field f : c.getDeclaredFields()) list.add(f);
		return list;
	}
	
	public static List<Class<?>> getAllPerant(Class<?> clazz) {
		final List<Class<?>> list = new ArrayList<>();
		while(!clazz.equals(Object.class) && (clazz != null)) {
			list.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	public static <A extends Annotation> List<A> getAnnotations(final Class<?> clazz, final Class<A> annotationClass) {
		List<A> res = new ArrayList<>();
		if(clazz.equals(Object.class))return res;
		for(final Class<?> c : clazz.getInterfaces()) {
			final A annotation = c.getAnnotation(annotationClass);
			if(annotation != null) res.add(annotation);
		}
		final A annotation = clazz.getAnnotation(annotationClass);
		if(annotation != null) res.add(annotation);
		res.addAll(getAnnotations(clazz.getSuperclass(), annotationClass));
		return res;
	}
	
	//	public static <T> T clone(T t) {
	//		Class<T> clazz = (Class<T>) t.getClass();
	//		clazz.getConstructor();
	//	}
}
