package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class ByteLoader extends PrimitiveLoader<Byte> {
	
	public ByteLoader() {
		super(byte.class);
	}
	
	@Override
	public Byte parse(final String value) throws NumberFormatException {
		return Byte.parseByte(value);
	}
}
