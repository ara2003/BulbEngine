package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public interface SimpleValueLoader<R> extends ValueLoader {

	@Override
	default <T> T parse(Class<T> clazz, String value) throws Exception {
		if(!this.isLoaded(clazz)) throw new UnsupportedOperationException("not loadeble class");
		return this.parse(value);
	}
	
	<T extends R> T parse(String value) throws Exception;
	
}
