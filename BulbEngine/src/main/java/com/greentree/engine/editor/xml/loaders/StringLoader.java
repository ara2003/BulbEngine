package com.greentree.engine.editor.xml.loaders;

import com.greentree.engine.editor.xml.AbstractLoader;

/** @author Arseny Latyshev */
public class StringLoader extends AbstractLoader<String> {
	
	public StringLoader() {
		super(String.class);
	}
	
	@Override
	public String load(String value) {
		return value;
	}
}
