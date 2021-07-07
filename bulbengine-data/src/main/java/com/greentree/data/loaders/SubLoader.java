package com.greentree.data.loaders;

import java.util.function.Function;

import com.greentree.common.triple.Triple;

public abstract class SubLoader implements Loader {

	private Function<Triple<Class<?>, String, Object>, Object> function;

	public final void setLoad(Function<Triple<Class<?>, String, Object>, Object> function) {
		this.function = function;
	}

	public final Object subLoad(Class<?> clazz, String value) throws Exception {
		return subLoad(clazz, value, null);
	}
	public final Object subLoad(Class<?> clazz, String value, Object object) throws Exception {
		return function.apply(new Triple<>(clazz, value, object));
	}

}
