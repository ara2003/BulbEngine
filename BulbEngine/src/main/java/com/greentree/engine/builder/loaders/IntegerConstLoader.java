package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.value.PrimitiveLoader;
import com.greentree.engine.Windows;

/** @author Arseny Latyshev */
public class IntegerConstLoader extends PrimitiveLoader<Integer> {
	
	public IntegerConstLoader() {
		super(int.class);
	}
	
	@Override
	public Integer parse(final String value) {
		if(value.equals("window::width")) return Windows.getWindow().getWidth();
		if(value.equals("window::height")) return Windows.getWindow().getHeight();
		throw new UnsupportedOperationException(value + " is not Integer Const");
	}
}
