package com.greentree.data.loaders.value;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/** @author Arseny Latyshev */
public class StaticFieldLoader implements ValueLoader {
	
	@Override
	public boolean isLoaded(final Class<?> clazz) {
		if(clazz.isEnum()) return false;
		if(clazz.isPrimitive()) return false;
		for(Field f : clazz.getFields())if((f.getModifiers() & Modifier.STATIC) != 0)return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(final Class<T> fieldClass, final String value) throws Exception {
		final Field field = fieldClass.getField(Objects.requireNonNull(value));
		if((field.getModifiers() & Modifier.STATIC) == 0)
			throw new UnsupportedOperationException("not static");
		try {
			return (T) field.get(null);
		}catch(final ExceptionInInitializerError e) {
			throw new UnsupportedOperationException(e);
		}
	}
	
}
