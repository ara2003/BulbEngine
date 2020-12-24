package com.greentree.engine.script;


/**
 * @author Arseny Latyshev
 */
public abstract class Type {
	
	protected byte[] bytes;
	
	public Type(){
		bytes = new byte[getCountBytes()];
	}
	
	public abstract byte[] getBytes();
	public abstract int getCountBytes();
	public abstract String getName();
	
}
