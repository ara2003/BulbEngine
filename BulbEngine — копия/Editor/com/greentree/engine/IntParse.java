package com.greentree.engine;

import java.io.InputStream;

import com.greentree.engine.input.Input;

/**
 * @author Arseny Latyshev
 *
 */
public class IntParse {

	public static int parseInt(String value) {
		if(value.startsWith("key::"))return Input.getKeyIndex(value.substring(5));
		return Integer.parseInt(value);
	}
}
