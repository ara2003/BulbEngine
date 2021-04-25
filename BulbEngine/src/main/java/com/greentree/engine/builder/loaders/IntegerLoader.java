package com.greentree.engine.builder.loaders;

import com.greentree.engine.Windows;

/** @author Arseny Latyshev */
public class IntegerLoader extends PrimitiveLoader<Integer> {
	
	public IntegerLoader() {
		super(int.class);
	}
	
	@Override
	public Integer load(final String value) {
		if(value.equals("window::width")) return Windows.getWindow().getWidth();
		if(value.equals("window::height")) return Windows.getWindow().getHeight();
		return Integer.parseInt(value);
	}
}
