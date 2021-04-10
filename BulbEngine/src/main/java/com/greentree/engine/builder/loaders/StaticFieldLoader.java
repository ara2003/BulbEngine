package com.greentree.engine.builder.loaders;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/** @author Arseny Latyshev */
public class StaticFieldLoader implements Loader {
	
	@Override
	public boolean isLoadedClass(final Class<?> clazz) {
		if(clazz.isEnum()) return false;
		if(clazz.isPrimitive()) return false;
		return true;
	}
	
	@Override
	public Object load(final Class<?> fieldClass, final String value) throws Exception {
		final Field field = fieldClass.getField(Objects.requireNonNull(value));
		if((field.getModifiers() & Modifier.STATIC) == 0)
			throw new UnsupportedOperationException("not static");
		try {
			return field.get(null);
		}catch(final ExceptionInInitializerError e) {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public Object load(final String value) throws Exception {
		throw new UnsupportedOperationException("use load(Class clazz, String value)");
	}
}
