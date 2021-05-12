package com.greentree.engine.core.builder.loaders;

/** @author Arseny Latyshev */
public class DoubleLoader extends com.greentree.engine.core.builder.loaders.PrimitiveLoader<Double> {
	
	public DoubleLoader() {
		super(double.class);
	}
	
	@Override
	public Double load(final String value) throws Exception {
		return Double.parseDouble(value);
	}
}
