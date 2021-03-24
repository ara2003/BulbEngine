package com.greentree.engine.editor.xml.loaders;

import com.greentree.engine.editor.xml.PairLoader;

/** @author Arseny Latyshev */
public class ByteLoader extends PairLoader<Byte> {
	
	public ByteLoader() {
		super(Byte.class, byte.class);
	}
	
	@Override
	public Object load(String value) throws Exception {
		return Byte.parseByte(value);
	}
}
