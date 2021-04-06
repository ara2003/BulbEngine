package com.greentree.engine.editor.loaders;

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
	private final LoadingCache<Class<?>, Collection<Loader>> getKesh = CacheBuilder.newBuilder().softValues()
		.build(new CacheLoader<>() {
			
			@Override
			public Collection<Loader> load(final Class<?> clazz) {
				return LoaderList.this.get0(clazz);
			}
		});
	
	
	
	public LoaderList() {
		this.list = new ArrayList<>();
	}
	
	public void addLoader(final Loader loader) {
		this.list.add(loader);
	}
	
	public Collection<Loader> get(final Class<?> clazz) {
		try {
			return this.getKesh.get(clazz);
		}catch(final ExecutionException e) {
		}
		return this.get0(clazz);
	}
	
	private Collection<Loader> get0(final Class<?> clazz) {
		final Collection<Loader> res = new ArrayList<>();
		for(final Loader loader : this.list) if(loader.isLoadedClass(clazz)) res.add(loader);
		return res;
	}
	
	public boolean hasLoader(final Class<? extends Loader> cl) {
		return this.list.parallelStream().anyMatch(e->e.getClass().equals(cl));
	}
	
	@Override
	public Iterator<Loader> iterator() {
		return this.list.iterator();
	}
	
	@SuppressWarnings("unchecked")
	public <R> R load(final Class<R> clazz, final String xmlValue) {
		final Collection<Exception> exception = new ArrayList<>();
		for(final Loader loader : this.get(clazz)) try {
			final R r = (R) loader.load(clazz, xmlValue);
			return r;
		}catch(final Exception e) {
			exception.add(e);
		}
		if(clazz.isPrimitive()) return null;
		for(final Exception e : exception) e.printStackTrace();
		throw new UnsupportedOperationException(String.format("%s as %s in %s %s", xmlValue, clazz.getName(), this.get(clazz), exception));
		
	}
}
