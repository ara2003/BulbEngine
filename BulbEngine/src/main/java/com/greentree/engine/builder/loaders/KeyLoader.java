package com.greentree.engine.builder.loaders;

import com.greentree.graphics.input.Key;

/**
 * @author Arseny Latyshev
 *
 */
public class KeyLoader extends AbstractLoader<Key> {
	
	public KeyLoader() {
		super(Key.class);
	}
	
	@Override
	public Key load(String value) throws Exception {
		return Key.valueOf(value);
	}
	
}
