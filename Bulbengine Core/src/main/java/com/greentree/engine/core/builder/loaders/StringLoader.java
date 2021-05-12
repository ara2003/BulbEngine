package com.greentree.engine.core.builder.loaders;

/** @author Arseny Latyshev */
public class StringLoader extends AbstractLoader<String> {
	
	public StringLoader() {
		super(String.class);
	}
	
	@Override
	public String load(final String value) {
		if(value == null) return "";
		return value;
	}
}
