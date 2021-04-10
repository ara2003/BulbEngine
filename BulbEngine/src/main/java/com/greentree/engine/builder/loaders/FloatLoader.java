package com.greentree.engine.builder.loaders;

/** @author Arseny Latyshev */
public class FloatLoader extends PrimitiveLoader<Float> {
	
	public FloatLoader() {
		super(float.class);
	}
	
	@Override
	public Float load(final String value) {
		return Float.parseFloat(value);
	}
}
