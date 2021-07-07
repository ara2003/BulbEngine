package com.greentree.engine.builder.loaders;

import com.greentree.data.loaders.value.PrimitiveLoader;
import com.greentree.engine.Windows;

/** @author Arseny Latyshev */
public class IntegerConstLoader extends PrimitiveLoader {

	public IntegerConstLoader() {
		super(int.class);
	}

	@Override
	public Integer parse(final String value) {
		if("window::width".equals(value)) return Windows.getWindow().getWidth();
		if("window::height".equals(value)) return Windows.getWindow().getHeight();
		throw new UnsupportedOperationException(value + " is not Integer Const");
	}

}
