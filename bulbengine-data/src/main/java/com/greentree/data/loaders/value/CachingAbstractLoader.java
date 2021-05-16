package com.greentree.data.loaders.value;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.greentree.data.loaders.AbstractLoader;

/** @author Arseny Latyshev */
public abstract class CachingAbstractLoader<C> extends AbstractLoader<C> implements SimpleValueLoader<C> {
	
	private final LoadingCache<String, ? extends C> cache = CacheBuilder.newBuilder().softValues()
		.build(new CacheLoader<>() {

			@Override
			public C load(String value) throws Exception {
				return CachingAbstractLoader.this.load0(value);
			}
			
		});
	
	public CachingAbstractLoader(final Class<C> LoadengClazz) {
		super(LoadengClazz);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final <T extends C> T parse(final String value) throws Exception {
		try {
			return (T) this.cache.get(value);
		}catch(final ExecutionException e) {
			e.printStackTrace();
		}
		return this.load0(value);
	}
	
	public abstract <T extends C> T load0(String value) throws Exception;
	
	
	
}
