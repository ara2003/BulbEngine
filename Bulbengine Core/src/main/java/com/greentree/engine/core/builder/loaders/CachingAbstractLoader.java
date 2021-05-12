package com.greentree.engine.core.builder.loaders;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/** @author Arseny Latyshev */
public abstract class CachingAbstractLoader<C> extends AbstractLoader<C> {
	
	private final LoadingCache<String, C> cache = CacheBuilder.newBuilder().softValues()
		.build(new CacheLoader<>() {
			
			@Override
			public C load(final String value) throws Exception {
				return CachingAbstractLoader.this.load0(value);
			}
		});
	
	public CachingAbstractLoader(final Class<C> LoadengClazz) {
		super(LoadengClazz);
	}
	
	@Override
	public final C load(final String value) throws Exception {
		try {
			return this.cache.get(value);
		}catch(final ExecutionException e) {
			e.printStackTrace();
		}
		return this.load0(value);
	}
	
	public abstract C load0(String value) throws Exception;
	
	
	
}
