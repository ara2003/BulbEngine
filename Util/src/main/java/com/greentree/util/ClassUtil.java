package com.greentree.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ClassUtil {
	
	public synchronized static Collection<Field> getAllFields(final Class<?> clazz) {
		final Collection<Field> list = new ArrayList<>();
		for(final Class<?> c : getAllPerant(clazz)) Collections.addAll(list, c.getDeclaredFields());
		return list;
	}
	
	public synchronized static Collection<Class<?>> getAllPerant(Class<?> clazz) {
		final Collection<Class<?>> list = new ArrayList<>();
		while(!clazz.equals(Object.class) && clazz != null) {
			list.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	public synchronized static <A extends Annotation> Collection<A> getAllAnnotations(final Class<?> clazz,
			final Class<A> annotationClass) {
		Collection<A> res = new ArrayList<>();
		if(clazz == null) return res;
		if(clazz.equals(Object.class)) return res;
		if(annotationClass == null) return res;
		for(final Class<?> c : clazz.getInterfaces()) {
			final A annotation = c.getAnnotation(annotationClass);
			if(annotation != null) res.add(annotation);
		}final A annotation = clazz.getAnnotation(annotationClass);
		if(annotation != null) res.add(annotation);
		res.addAll(getAllAnnotations(clazz.getSuperclass(), annotationClass));
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static <T> Set<Class<? extends T>> getClases(Collection<? extends T> components) {
		final Set<Class<? extends T>> res = new HashSet<>();
		if(components == null) return res;
		for(T t : components) if(t != null) res.add((Class<? extends T>) t.getClass());
		return res;
	}
}
