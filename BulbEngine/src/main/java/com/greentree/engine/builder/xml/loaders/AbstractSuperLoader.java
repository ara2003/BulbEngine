package com.greentree.engine.builder.xml.loaders;

import java.util.function.Function;

import com.greentree.engine.builder.loaders.AbstractLoader;

/** @author Arseny Latyshev */
public abstract class AbstractSuperLoader<C> extends AbstractLoader<C> implements SuperLoader {
	
	private Function<String, ?> function;
	
	public AbstractSuperLoader(final Class<C> LoadengClazz) {
		super(LoadengClazz);
	}
	
	@Override
	public void setLoad(final Function<String, ?> function) {
		this.function = function;
	}
	
	@Override
	public Object subLoad(final String value) {
		return function.apply(value);
	}
	
}
