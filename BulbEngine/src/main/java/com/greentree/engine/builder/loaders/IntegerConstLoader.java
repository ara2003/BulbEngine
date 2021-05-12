package com.greentree.engine.builder.loaders;

import com.greentree.engine.Windows;
import com.greentree.engine.core.builder.loaders.PrimitiveLoader;

/** @author Arseny Latyshev */
public class IntegerConstLoader extends PrimitiveLoader<Integer> {
	
	public IntegerConstLoader() {
		super(int.class);
	}
	
	@Override
	public Integer load(final String value) {
		if(value.equals("window::width")) return Windows.getWindow().getWidth();
		if(value.equals("window::height")) return Windows.getWindow().getHeight();
		throw new UnsupportedOperationException(value + " isn\'t Integer Const");
	}
}
