package com.greentree.engine.core.builder.loaders;

/** @author Arseny Latyshev */
public class IntegerLoader extends PrimitiveLoader<Integer> {
	
	public IntegerLoader() {
		super(int.class);
	}
	
	@Override
	public Integer load(final String value) {
		return Integer.parseInt(value);
	}
}
