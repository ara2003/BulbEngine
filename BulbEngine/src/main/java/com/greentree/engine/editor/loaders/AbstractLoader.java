package com.greentree.engine.editor.loaders;

/** @author Arseny Latyshev */
public abstract class AbstractLoader<C> implements Loader {
	
	private final Class<C> clazz;
	
	public AbstractLoader(final Class<C> LoadengClazz) {
		this.clazz = LoadengClazz;
	}
	
	@Override
	public boolean isLoadedClass(final Class<?> clazz) {
		return clazz.isAssignableFrom(this.clazz);
	}
	
	@Override
	public abstract C load(String value) throws Exception;
}
