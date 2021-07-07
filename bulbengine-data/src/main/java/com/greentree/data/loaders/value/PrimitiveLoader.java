package com.greentree.data.loaders.value;

import java.util.HashMap;
import java.util.Map;

/** @author Arseny Latyshev */
public abstract class PrimitiveLoader implements SimpleValueLoader {

	private static final Map<Class<?>, Class<?>> primitiveTo = new HashMap<>();

	static {
		PrimitiveLoader.primitiveTo.put(float.class, Float.class);
		PrimitiveLoader.primitiveTo.put(int.class, Integer.class);
		PrimitiveLoader.primitiveTo.put(boolean.class, Boolean.class);
		PrimitiveLoader.primitiveTo.put(char.class, Character.class);
		PrimitiveLoader.primitiveTo.put(double.class, Double.class);
		PrimitiveLoader.primitiveTo.put(byte.class, Byte.class);
		PrimitiveLoader.primitiveTo.put(short.class, Short.class);
	}

	private final Class<?> classa, classb;

	public PrimitiveLoader(final Class<?> primitiveClass) {
		if(!primitiveClass.isPrimitive()) throw new IllegalArgumentException("primitiveClass is not primitive");
		this.classa = primitiveClass;
		this.classb = PrimitiveLoader.primitiveTo.get(primitiveClass);
	}

	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return this.classb.isAssignableFrom(clazz) || this.classa.isAssignableFrom(clazz);
	}
	
	@Override
	public abstract Object parse(String value) throws Exception;
}
