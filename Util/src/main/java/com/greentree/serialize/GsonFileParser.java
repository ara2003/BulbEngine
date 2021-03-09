package com.greentree.serialize;

import com.google.gson.Gson;

/**
 * @author Arseny Latyshev
 *
 */
@Deprecated
public class GsonFileParser extends FileParser {

	private final static Gson gson = new Gson();
	
	public GsonFileParser() {
		super((text, clazz)->gson.fromJson(text, clazz), obj->gson.toJson(obj));
	}
	
	
}
