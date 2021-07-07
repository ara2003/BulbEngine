package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class DoubleLoader extends PrimitiveLoader {
	
	public DoubleLoader() {
		super(double.class);
	}
	
	@Override
	public Double parse(final String value) throws Exception {
		return Double.parseDouble(value);
	}
}
