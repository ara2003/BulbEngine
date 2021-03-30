package com.greentree.engine.editor.loaders;

import com.greentree.engine.editor.PrimitiveLoader;

/** @author Arseny Latyshev */
public class ShortLoader extends PrimitiveLoader<Short> {
	
	public ShortLoader() {
		super(short.class);
	}
	
	@Override
	public Short load(final String value) throws Exception {
		return Short.parseShort(value);
	}
}
