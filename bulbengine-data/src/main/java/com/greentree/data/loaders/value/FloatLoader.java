package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class FloatLoader extends PrimitiveLoader<Float> {
	
	public FloatLoader() {
		super(float.class);
	}
	
	@Override
	public Float parse(final String value) {
		return Float.parseFloat(value);
	}
}
