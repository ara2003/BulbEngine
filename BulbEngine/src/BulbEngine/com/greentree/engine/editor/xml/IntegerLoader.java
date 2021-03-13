package com.greentree.engine.editor.xml;

import com.greentree.engine.input.listeners.Input;

/** @author Arseny Latyshev */
public class IntegerLoader extends ClassLoader<Integer> {
	
	@Override
	public Integer load(String value) {
		if(value.startsWith("key::")) return Input.getIndexOfKey(value.substring(5));
		return Integer.parseInt(value);
	}
}
