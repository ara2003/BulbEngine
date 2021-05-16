package com.greentree.data.loaders.value;

import com.greentree.data.loaders.AbstractLoader;

/** @author Arseny Latyshev */
public class StringLoader extends AbstractLoader<String> implements SimpleValueLoader<String> {
	
	public StringLoader() {
		super(String.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String parse(final String value) {
		if(value == null) return "";
		return value;
	}
}
