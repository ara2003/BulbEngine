package com.greentree.engine.editor.xml;


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
