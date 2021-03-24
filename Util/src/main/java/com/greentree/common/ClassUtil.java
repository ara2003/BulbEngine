package com.greentree.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class ClassUtil {
	
	public static <A extends Annotation> Collection<A> getAllAnnotations(final Class<?> clazz,
			final Class<A> annotationClass) {
		Collection<A> res = new ArrayList<>();
		if(clazz == null) return res;
		if(clazz.equals(Object.class)) return res;
		if(annotationClass == null) return res;
		for(final Class<?> c : clazz.getInterfaces()) {
			final A annotation = c.getAnnotation(annotationClass);
			if(annotation != null) res.add(annotation);
		}
		final A annotation = clazz.getAnnotation(annotationClass);
		if(annotation != null) res.add(annotation);
		res.addAll(getAllAnnotations(clazz.getSuperclass(), annotationClass));
		return res;
	}
	
	public static List<Field> getAllFields(final Class<?> clazz) {
		final List<Field> list = new LinkedList<>();
		for(final Class<?> c : getAllPerant(clazz)) Collections.addAll(list, c.getDeclaredFields());
		return list;
	}
	
	public static <A extends Annotation> List<Field> getAllFieldsWithAnnotation(Class<?> clazz, Class<A> annotation) {
		List<Field> list = getAllFields(clazz);
		list.removeIf(a -> a.getAnnotation(annotation) == null);
		return list;
	}
	
	public static Collection<Class<?>> getAllPerant(Class<?> clazz) {
		final Collection<Class<?>> list = new ArrayList<>();
		while(!clazz.equals(Object.class) && clazz != null) {
			list.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Set<Class<? extends T>> getClases(Iterable<T> object) {
		final Set<Class<? extends T>> res = new HashSet<>();
		if(object == null) return res;
		for(T t : object) if(t != null) res.add((Class<? extends T>) t.getClass());
		return res;
	}

	public static <T> T newInstance(Class<T> clazz) {
		if(clazz == null) throw new NullPointerException("clazz is null");
		try {
			final Constructor<T> constructor = clazz.getConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			Log.error(e);
		}
		return null;
	}
}
