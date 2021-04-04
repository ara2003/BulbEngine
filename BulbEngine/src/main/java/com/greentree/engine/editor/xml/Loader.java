package com.greentree.engine.editor.xml;

/** @author Arseny Latyshev */
public interface Loader {
	
	boolean isLoadedClass(Class<?> clazz);
	
	default Object load(final Class<?> clazz, final String value) throws Exception {
		return this.load(value);
	}
	
	Object load(String value) throws Exception;
}
