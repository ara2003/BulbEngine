package com.greentree.engine.editor.xml.loaders;

import com.greentree.engine.editor.xml.PairLoader;

/**
 * @author Arseny Latyshev
 *
 */
public class FloatLoader extends PairLoader<Float> {

	public FloatLoader() {
		super(Float.class, float.class);
	}
	
	@Override
	public Float load(String value) {
		return Float.parseFloat(value);
	}
}
