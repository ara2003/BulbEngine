package com.greentree.engine.editor.loaders;

import java.lang.reflect.Field;

import com.greentree.engine.core.editor.Loader;

/** @author Arseny Latyshev */
public class StaticFieldLoader implements Loader {
	
	@Override
	public boolean isLoadedClass(final Class<?> clazz) {
		return true;
	}
	
	@Override
	public Object load(final Class<?> fieldClass, final String value) throws Exception {
		final Field field = fieldClass.getField(value);
		return field.get(null);
	}
	
	@Override
	public Object load(final String value) throws Exception {
		throw new UnsupportedOperationException("use load(Class clazz, String value)");
	}
}
