package com.greentree.engine;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import com.greentree.engine.input.Input;

/**
 * @author Arseny Latyshev
 */
public abstract class Builder<T> {
	
	
	public final void createNode(GameNode node, InputStream in) {
		createNode(node, parse(in));
	}
	protected abstract void createNode(GameNode node, T in);
	
	public abstract GameNode createNode(String prefab);
	
	public final String getNodeName(InputStream in) {
		return getNodeName(parse(in));
	}
	
	public abstract String getNodeName(T element);
	
	public abstract T parse(InputStream input);
	
}
