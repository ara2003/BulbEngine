package com.greentree.engine.editor.xml;

import java.lang.reflect.Field;

/** @author Arseny Latyshev */
public class StaticFieldLoader implements Loader {
	
	@Override
	public Object load(final Class<?> fieldClass, final String value) throws Exception {
		final Field field = fieldClass.getField(value);
		if(field.canAccess(null)) return field.get(null);
		return null;
	}
}
