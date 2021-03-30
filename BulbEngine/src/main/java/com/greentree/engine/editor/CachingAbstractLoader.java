package com.greentree.engine.editor;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class CachingAbstractLoader<C> extends AbstractLoader<C> {

	public CachingAbstractLoader(Class<C> LoadengClazz) {
		super(LoadengClazz);
	}

	private final LoadingCache<String, C> cache = CacheBuilder.newBuilder().softValues()
			.build(new CacheLoader<>() {
				@Override
				public C load(final String value) throws Exception {
					return load0(value);
				}
			});
	
	public abstract C load0(String value) throws Exception;
	
	@Override
	public final C load(String value) throws Exception {
		try {
			return cache.get(value);
		}catch(ExecutionException e) {
			e.printStackTrace();
		}
		return load0(value);
	}
	
	
	
}
