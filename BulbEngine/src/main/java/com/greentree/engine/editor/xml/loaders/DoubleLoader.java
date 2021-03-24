package com.greentree.engine.editor.xml.loaders;

/** @author Arseny Latyshev */
public class DoubleLoader extends com.greentree.engine.editor.xml.PairLoader<Double> {
	
	public DoubleLoader() {
		super(Double.class, double.class);
	}
	
	@Override
	public Double load(final String value) throws Exception {
		return Double.parseDouble(value);
	}
}
