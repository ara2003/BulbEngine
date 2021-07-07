package com.greentree.data.loaders.value;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/** @author Arseny Latyshev */
public class StaticFieldLoader implements ValueLoader {

	@Override
	public boolean isLoaded(final Class<?> clazz) {
		if(clazz.isEnum() || clazz.isPrimitive()) return false;
		for(Field f : clazz.getFields())if((f.getModifiers() & (Modifier.STATIC | Modifier.FINAL)) != 0&&clazz.isAssignableFrom(f.getType()))return true;
		return false;
	}

	@Override
	public Object parse(final Class<?> fieldClass, final String value) throws Exception {
		final Field field = fieldClass.getField(Objects.requireNonNull(value));
		if((field.getModifiers() & Modifier.STATIC) == 0)
			throw new UnsupportedOperationException("not static");
		try {
			return field.get(null);
		}catch(final ExceptionInInitializerError e) {
			throw new UnsupportedOperationException(e);
		}
	}

}
