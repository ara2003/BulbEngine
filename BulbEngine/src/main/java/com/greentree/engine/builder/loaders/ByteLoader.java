package com.greentree.engine.editor.loaders;

/** @author Arseny Latyshev */
public class ByteLoader extends PrimitiveLoader<Byte> {
	
	public ByteLoader() {
		super(byte.class);
	}
	
	@Override
	public Byte load(final String value) throws Exception {
		return Byte.parseByte(value);
	}
}
