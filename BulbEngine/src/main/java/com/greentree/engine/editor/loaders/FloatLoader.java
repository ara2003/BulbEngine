package com.greentree.engine.editor.loaders;

import com.greentree.engine.core.editor.PrimitiveLoader;

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
