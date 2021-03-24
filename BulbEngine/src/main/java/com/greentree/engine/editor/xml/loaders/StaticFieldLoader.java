package com.greentree.engine.editor.xml.loaders;

import java.lang.reflect.Field;

import com.greentree.engine.editor.xml.Loader;

/** @author Arseny Latyshev */
public class StaticFieldLoader implements Loader {
	
	@Override
	public Object load(final Class<?> fieldClass, final String value) throws Exception {
		final Field field = fieldClass.getField(value);
		if(field.canAccess(null)) return field.get(null);
		return null;
	}
}
