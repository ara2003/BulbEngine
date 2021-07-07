package com.greentree.data.loaders.value;

import com.greentree.data.loaders.AbstractLoader;

/** @author Arseny Latyshev */
public abstract class AbstractVlaueLoader extends AbstractLoader implements SimpleValueLoader {
	
	public AbstractVlaueLoader(final Class<?> clazz) {
		super(clazz);
	}
	
}
