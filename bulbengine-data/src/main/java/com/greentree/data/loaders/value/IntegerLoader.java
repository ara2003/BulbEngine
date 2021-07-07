package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class IntegerLoader extends PrimitiveLoader {

	public IntegerLoader() {
		super(int.class);
	}

	@Override
	public Integer parse(final String value) {
		return Integer.parseInt(value);
	}
}
