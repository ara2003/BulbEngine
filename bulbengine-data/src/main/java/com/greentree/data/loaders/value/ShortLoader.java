package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class ShortLoader extends PrimitiveLoader {
	
	public ShortLoader() {
		super(short.class);
	}
	
	@Override
	public Short parse(final String value) throws Exception {
		return Short.parseShort(value);
	}
}
