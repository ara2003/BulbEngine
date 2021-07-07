package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class EnumLoader implements ValueLoader {
	
	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return clazz.isEnum();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Object parse(Class<?> clazz, String value) throws Exception {
		try {
		return Enum.valueOf(clazz.asSubclass(Enum.class), value);
	}catch(final NoClassDefFoundError e) {
		throw new ClassNotFoundException(clazz.getName() + " " + value);
	}
	}
	
}
