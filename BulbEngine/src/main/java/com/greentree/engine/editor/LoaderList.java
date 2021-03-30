package com.greentree.engine.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/** @author Arseny Latyshev */
public class LoaderList implements Iterable<Loader> {
	
	private final List<Loader> list;
	private final LoadingCache<Class<?>, Iterable<Loader>> getKesh = CacheBuilder.newBuilder().softValues()
			.build(new CacheLoader<>() {
				@Override
				public Iterable<Loader> load(final Class<?> clazz) {
					return get0(clazz);
				}
			});
	
	public LoaderList() {
		this.list = new ArrayList<>();
	}
	
	public void addLoader(final Loader loader) {
		this.list.add(loader);
	}
	
	private Iterable<Loader> get0(Class<?> clazz) {
		final Collection<Loader> res = new ArrayList<>();
		for(final Loader loader : list) if(loader.isLoadedClass(clazz)) res.add(loader);
		return res;
	} 
	
	public Iterable<Loader> get(final Class<?> clazz) {
		try {
			return getKesh.get(clazz);
		}catch(ExecutionException e) {
		}
		return get0(clazz);
	}
	
	@Override
	public Iterator<Loader> iterator() {
		return this.list.iterator();
	}
	
	@SuppressWarnings("unchecked")
	public <R> R load(final Class<R> clazz, final String xmlValue) {
		for(final Loader loader : this.get(clazz)) {
			try {
				return (R) loader.load(clazz, xmlValue);
			}catch(final Exception e) {
			}
		}
		if(clazz.isPrimitive())return null;
		throw new UnsupportedOperationException(String.format("%s as %s in %s", xmlValue, clazz.getName(), get(clazz)));
	}
}
