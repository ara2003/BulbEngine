package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class CharLoader extends PrimitiveLoader {
	
	public CharLoader() {
		super(char.class);
	}
	
	@Override
	public Character parse(final String value) throws Exception {
		return value.charAt(0);
	}
}
