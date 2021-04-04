package com.greentree.engine.editor.loaders;

import com.greentree.engine.editor.xml.Loader;

/** @author Arseny Latyshev */
public class EnumLoader implements Loader {
	
	@Override
	public boolean isLoadedClass(final Class<?> clazz) {
		return clazz.isEnum();
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public Enum load(final Class<?> clazz, final String value) throws ClassNotFoundException {
		try {
		return Enum.valueOf(clazz.asSubclass(Enum.class), value);
		}catch (NoClassDefFoundError e) {
			throw new ClassNotFoundException(clazz.getName() + " " + value);
		}
	}
	
	@Override
	public Object load(final String value) throws Exception {
		throw new UnsupportedOperationException("use load(Class enum, String name)");
	}
}
