package com.greentree.engine.editor.loaders;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.greentree.engine.editor.xml.Loader;

/** @author Arseny Latyshev */
public class StaticFieldLoader implements Loader {
	
	@Override
	public boolean isLoadedClass(final Class<?> clazz) {
//		if(clazz.isEnum())return false;
		
		return true;
	}
	
	@Override
	public Object load(final Class<?> fieldClass, final String value) throws Exception {
		final Field field = fieldClass.getField(value);
		if((field.getModifiers() & Modifier.STATIC) == 0)
			throw new UnsupportedOperationException("not static");
		try {
			return field.get(null);
		}catch(ExceptionInInitializerError e) {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public Object load(final String value) throws Exception {
		throw new UnsupportedOperationException("use load(Class clazz, String value)");
	}
}
