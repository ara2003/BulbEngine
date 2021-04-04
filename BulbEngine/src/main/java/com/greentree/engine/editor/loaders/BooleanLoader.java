package com.greentree.engine.editor.loaders;

import com.greentree.engine.editor.xml.PrimitiveLoader;

/** @author Arseny Latyshev */
public class BooleanLoader extends PrimitiveLoader<Boolean> {
	
	public BooleanLoader() {
		super(boolean.class);
	}
	
	@Override
	public Boolean load(final String value) {
		return Boolean.parseBoolean(value);
	}
}
