package com.greentree.engine.core.builder.loaders;

/** @author Arseny Latyshev */
public interface Loader  {

	default Object load(final Class<?> clazz, final String value) throws Exception {
		if(!this.isLoaded(clazz)) throw new UnsupportedOperationException("not loadeble class");
		return this.load(value);
	}
	
	boolean isLoaded(Class<?> clazz);

	Object load(String value) throws Exception;
	
}
