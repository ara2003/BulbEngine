package com.greentree.data.loaders.value;

import java.util.HashMap;
import java.util.Map;

/** @author Arseny Latyshev */
public abstract class PrimitiveLoader<C> implements SimpleValueLoader<C> {

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

	private final Class<C> classa, classb;

	@SuppressWarnings("unchecked")
	public PrimitiveLoader(final Class<C> primitiveClass) {
		if(!primitiveClass.isPrimitive()) throw new IllegalArgumentException("primitiveClass is not primitive");
		this.classa = primitiveClass;
		this.classb = (Class<C>) PrimitiveLoader.primitiveTo.get(primitiveClass);
	}

	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return this.classb.isAssignableFrom(clazz) || this.classa.isAssignableFrom(clazz);
	}
	@SuppressWarnings("unchecked")
	@Override
	public abstract C parse(String value) throws Exception;
}
