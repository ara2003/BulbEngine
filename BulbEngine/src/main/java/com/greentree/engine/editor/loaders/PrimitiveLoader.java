package com.greentree.engine.editor.loaders;

import java.util.HashMap;
import java.util.Map;

/** @author Arseny Latyshev */
public abstract class PrimitiveLoader<C> implements Loader {
	
	private final Class<C> classa, classb;
	private static final Map<Class<?>, Class<?>> primitiveTo = new HashMap<>();
	
	static {
		primitiveTo.put(float.class, Float.class);
		primitiveTo.put(int.class, Integer.class);
		primitiveTo.put(boolean.class, Boolean.class);
		primitiveTo.put(char.class, Character.class);
		primitiveTo.put(double.class, Double.class);
		primitiveTo.put(byte.class, Byte.class);
		primitiveTo.put(short.class, Short.class);
	}
	
	@SuppressWarnings("unchecked")
	public PrimitiveLoader(final Class<C> primitiveClass) {
		if(!primitiveClass.isPrimitive()) throw new IllegalArgumentException("primitiveClass is not primitive");
		this.classa = primitiveClass;
		this.classb = (Class<C>) primitiveTo.get(primitiveClass);
	}
	
	@Override
	public boolean isLoadedClass(final Class<?> clazz) {
		return this.classb.isAssignableFrom(clazz) || this.classa.isAssignableFrom(clazz);
	}
	
	@Override
	public abstract C load(String value) throws Exception;
}
