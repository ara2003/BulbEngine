package com.greentree.data.loaders.collection;

import com.greentree.data.loaders.Loader2;
import com.greentree.data.loaders.SubLoader;


public abstract class AbstactSubLoader<C> extends SubLoader implements Loader2 {

	private final Class<C> clazz;
	
	public AbstactSubLoader(final Class<C> LoadengClazz) {
		this.clazz = LoadengClazz;
	}
	
	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return this.clazz.isAssignableFrom(clazz);
	}
	
}
