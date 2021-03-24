package com.greentree.engine.editor.xml.loaders;

import com.greentree.engine.editor.xml.PairLoader;

/** @author Arseny Latyshev */
public class CharLoader extends PairLoader<Character> {
	
	public CharLoader() {
		super(Character.class, char.class);
	}

	@Override
	public Object load(final String value) throws Exception {
		return null;
	}
}
