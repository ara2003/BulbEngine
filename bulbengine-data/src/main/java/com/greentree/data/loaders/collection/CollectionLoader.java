package com.greentree.data.loaders.collection;

import java.util.function.Function;

import com.greentree.common.pair.Pair;
import com.greentree.data.loaders.AbstractLoader;


public abstract class CollectionLoader<C> extends AbstractLoader<C> {

	private Function<Pair<Class<?>, String>, Object> function;

	public CollectionLoader(Class<C> LoadengClazz) {
		super(LoadengClazz);
	}
	
	public final Object subLoad(Class<?> g, String attribute) {
		return function.apply(new Pair<>(g, attribute));
	}

	public final void setLoad(Function<Pair<Class<?>, String>, Object> function) {
		this.function = function;
	}
	
}
