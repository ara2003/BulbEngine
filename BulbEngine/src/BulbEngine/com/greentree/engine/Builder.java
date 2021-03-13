package com.greentree.engine;

import java.io.InputStream;

/** @author Arseny Latyshev */
public abstract class Builder<T> {
	
	public final void createNode(GameObject node, InputStream in) {
		createNode(node, parse(in));
	}
	
	protected abstract void createNode(GameObject node, T in);
	public abstract GameObject createNode(String prefab);
	
	public final String getNodeName(InputStream in) {
		return getNodeName(parse(in));
	}
	
	public abstract String getNodeName(T element);
	public abstract T parse(InputStream input);
}
