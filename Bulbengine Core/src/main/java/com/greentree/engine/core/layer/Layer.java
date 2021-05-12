package com.greentree.engine.core.layer;


/** @author Arseny Latyshev */
public final class Layer {
	
	private final String name;
	
	public Layer(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Layer [name=" + name + "]";
	}
	
}
