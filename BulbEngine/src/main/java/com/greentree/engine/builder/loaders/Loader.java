package com.greentree.engine.builder.loaders;

/** @author Arseny Latyshev */
public interface Loader {
	
	boolean isLoadedClass(Class<?> clazz);
	
	default Object load(final Class<?> clazz, final String value) throws Exception {
		if(!this.isLoadedClass(clazz)) throw new UnsupportedOperationException("not loadeble class");
		return this.load(value);
	}
	
	Object load(String value) throws Exception;
}
