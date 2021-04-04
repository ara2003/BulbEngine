package com.greentree.engine.editor.loaders;

/** @author Arseny Latyshev */
public class DoubleLoader extends com.greentree.engine.editor.xml.PrimitiveLoader<Double> {
	
	public DoubleLoader() {
		super(double.class);
	}
	
	@Override
	public Double load(final String value) throws Exception {
		return Double.parseDouble(value);
	}
}
