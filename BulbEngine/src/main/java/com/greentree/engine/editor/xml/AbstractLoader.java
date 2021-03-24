package com.greentree.engine.editor.xml;

/** @author Arseny Latyshev */
public abstract class AbstractLoader<C> implements Loader {
	
	private final Class<C> clazz;
	
	public AbstractLoader(final Class<C> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public final Object load(final Class<?> fieldClass, final String value) throws Exception {
		if(fieldClass.isAssignableFrom(this.clazz)) return this.load(value);
		return null;
	}
	
	public abstract Object load(String value) throws Exception;
}