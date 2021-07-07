package com.greentree.data.loaders;

/** @author Arseny Latyshev */
public abstract class AbstractLoader implements Loader {
	
	private final Class<?> clazz;
	
	public AbstractLoader(final Class<?> LoadengClazz) {
		this.clazz = LoadengClazz;
	}
	
	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return this.clazz.isAssignableFrom(clazz);
	}
	
}
