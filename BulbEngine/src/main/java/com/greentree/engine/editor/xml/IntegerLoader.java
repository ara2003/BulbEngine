package com.greentree.engine.editor.xml;

import com.greentree.bulbgl.input.Input;

/** @author Arseny Latyshev */
public class IntegerLoader extends PairLoader<Integer> {
	
	public IntegerLoader() {
		super(Integer.class, int.class);
	}
	
	@Override
	public Integer load(String value) {
		if(value.startsWith("key::")) return Input.getIndexOfKey(value.substring(5));
		return Integer.parseInt(value);
	}
}
