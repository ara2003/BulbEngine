package com.greentree.engine.builder.loaders;

/** @author Arseny Latyshev */
public class ShortLoader extends PrimitiveLoader<Short> {
	
	public ShortLoader() {
		super(short.class);
	}
	
	@Override
	public Short load(final String value) throws Exception {
		return Short.parseShort(value);
	}
}
