package com.greentree.data.loaders;

/** @author Arseny Latyshev */
public abstract class AbstractLoader<C> implements Loader {
	
	private final Class<C> clazz;
	
	public AbstractLoader(final Class<C> LoadengClazz) {
		this.clazz = LoadengClazz;
	}
	
	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return this.clazz.isAssignableFrom(clazz);
	}
	
}
