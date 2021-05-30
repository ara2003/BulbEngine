package com.greentree.data.loaders;

import java.util.function.Function;

import com.greentree.common.pair.Pair;

public abstract class SubLoader implements Loader {

	private Function<Pair<Class<?>, String>, Object> function;

	public final void setLoad(Function<Pair<Class<?>, String>, Object> function) {
		this.function = function;
	}

	public final Object subLoad(Class<?> clazz, String value) throws Exception {
		return function.apply(new Pair<>(clazz, value));
	}

}
