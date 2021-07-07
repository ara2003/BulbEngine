package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public interface SimpleValueLoader extends ValueLoader {

	@Override
	default Object parse(Class<?> clazz, String value) throws Exception {
		if(!this.isLoaded(clazz)) throw new UnsupportedOperationException("not loadeble class");
		return this.parse(value);
	}
	

	Object parse(String value) throws Exception;
	
}
