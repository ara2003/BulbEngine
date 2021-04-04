package com.greentree.engine.editor.loaders;

import com.greentree.engine.editor.xml.PrimitiveLoader;

/** @author Arseny Latyshev */
public class CharLoader extends PrimitiveLoader<Character> {
	
	public CharLoader() {
		super(char.class);
	}
	
	@Override
	public Character load(final String value) throws Exception {
		return value.charAt(0);
	}
}
