package com.greentree.engine.editor.xml;


/**
 * @author Arseny Latyshev
 *
 */
public class BooleanLoader extends PairLoader<Boolean> {
	
	public BooleanLoader() {
		super(Boolean.class, boolean.class);
	}

	@Override
	public Object load(String value) {
		return Boolean.parseBoolean(value);
	}
}
