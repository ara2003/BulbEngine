package com.greentree.engine.script;

/**
 * @author Arseny Latyshev
 *
 */
public class Value {

	public Type type;
	public byte[] value;
	
	public Value(Type type) {
		this.type = type;
		value = new byte[type.getCountBytes()];
	}
	
}
