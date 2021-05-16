package com.greentree.data.loaders.value;

import com.greentree.data.loaders.AbstractLoader;

/** @author Arseny Latyshev */
public abstract class AbstractVlaueLoader<C> extends AbstractLoader<C> implements SimpleValueLoader<C> {
	
	public AbstractVlaueLoader(final Class<C> clazz) {
		super(clazz);
	}
	
}
