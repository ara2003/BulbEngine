package com.greentree.engine.editor.xml.loaders;

import com.greentree.engine.editor.xml.Loader;

/**
 * @author Arseny Latyshev
 *
 */
public class EnumLoader implements Loader {

	@SuppressWarnings("unchecked")
	@Override
	public Object load(Class<?> fieldClass, String value) {
		return Enum.valueOf(fieldClass.asSubclass(Enum.class), value);
	}
	
}
