package com.greentree.data.loaders.value;

/** @author Arseny Latyshev */
public class EnumLoader implements ValueLoader {
	
	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return clazz.isEnum();
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(Class<T> clazz, String value) throws Exception {
		try {
		return (T) Enum.valueOf(clazz.asSubclass(Enum.class), value);
	}catch(final NoClassDefFoundError e) {
		throw new ClassNotFoundException(clazz.getName() + " " + value);
	}
	}
	
}
