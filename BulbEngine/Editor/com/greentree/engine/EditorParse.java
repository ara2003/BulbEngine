package com.greentree.engine;

import com.greentree.engine.input.Input;

/**
 * @author Arseny Latyshev
 *
 */
public class EditorParse {
	
	public static int parseInt(String value) {
		if(value.startsWith("key::"))return Input.getKeyIndex(value.substring(5));
		return Integer.parseInt(value);
	}
}
