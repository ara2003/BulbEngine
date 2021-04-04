package com.greentree.engine.editor.loaders;

import com.greentree.bulbgl.input.Input;
import com.greentree.engine.Windows;
import com.greentree.engine.editor.xml.PrimitiveLoader;

/** @author Arseny Latyshev */
public class IntegerLoader extends PrimitiveLoader<Integer> {
	
	public IntegerLoader() {
		super(int.class);
	}
	
	@Override
	public Integer load(final String value) {
		if(value.startsWith("key::")) return Input.getIndexOfKey(value.substring(5));
		if(value.equals("window::width")) return Windows.getWindow().getWidth();
		if(value.equals("window::height")) return Windows.getWindow().getHeight();
		return Integer.parseInt(value);
	}
}
