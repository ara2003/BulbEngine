package com.greentree.data.loaders.value;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.greentree.data.loaders.AbstractLoader;

/** @author Arseny Latyshev */
public abstract class CachingAbstractLoader extends AbstractLoader implements SimpleValueLoader {
	
	private final LoadingCache<String, ?> cache = CacheBuilder.newBuilder().softValues()
		.build(new CacheLoader<>() {

			@Override
			public Object load(String value) throws Exception {
				return CachingAbstractLoader.this.load0(value);
			}
			
		});
	
	public CachingAbstractLoader(final Class<?> LoadengClazz) {
		super(LoadengClazz);
	}
	
	@Override
	public final Object parse(final String value) throws Exception {
		try {
			return this.cache.get(value);
		}catch(final ExecutionException e) {
			e.printStackTrace();
		}
		return this.load0(value);
	}
	
	public abstract Object load0(String value) throws Exception;
	
	
	
}
