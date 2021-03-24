package com.greentree.engine.editor.xml.loaders;

import com.greentree.engine.editor.xml.PairLoader;

/** @author Arseny Latyshev */
public class ShortLoader extends PairLoader<Short> {
	
	public ShortLoader() {
		super(Short.class, short.class);
	}
	
	@Override
	public Short load(final String value) throws Exception {
		return Short.parseShort(value);
	}
}
