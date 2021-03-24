package com.greentree.engine.editor.xml;

/** @author Arseny Latyshev */
public abstract class PairLoader<C> implements Loader {
	
	private final Class<C> classa, classb;
	
	public PairLoader(final Class<C> classa, final Class<C> classb) {
		if(classa.equals(classb)) throw new IllegalArgumentException("classa can not by equals classb");
		this.classa = classa;
		this.classb = classb;
	}
	
	@Override
	public final Object load(final Class<?> fieldClass, final String value) throws Exception {
		if(this.classb.isAssignableFrom(fieldClass)) return this.load(value);
		if(this.classa.isAssignableFrom(fieldClass)) return this.load(value);
		return null;
	}
	
	public abstract Object load(String value) throws Exception;
}
