package com.greentree.engine.builder.loaders;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.greentree.common.logger.Log;
import com.greentree.engine.core.builder.loaders.Loader;

/** @author Arseny Latyshev */
public class LoaderList implements Iterable<Loader>, Loader {
	
	private final List<Loader> list;
	private final LoadingCache<Class<?>, Collection<Loader>> getKesh = CacheBuilder.newBuilder().softValues()
		.build(new CacheLoader<>() {
			
			@Override
			public Collection<Loader> load(final Class<?> clazz) {
				return LoaderList.this.get0(clazz);
			}
		});
	
	public LoaderList() {
		list = new ArrayList<>();
	}
	
	public void addLoader(final Loader loader) {
		list.add(loader);
	}
	
	private Collection<Loader> get(final Class<?> clazz) {
		try {
			return getKesh.get(clazz);
		}catch(final ExecutionException e) {
		}
		return get0(clazz);
	}
	
	private Collection<Loader> get0(final Class<?> clazz) {
		final Collection<Loader> res = new ArrayList<>();
		
		ParameterizedType type;
		try {
			type = (ParameterizedType) LoaderList.class.getDeclaredField("list").getGenericType();
			Class.forName(type.getActualTypeArguments()[0].getTypeName());
		}catch(NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}catch(final ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		for(final Loader loader : list) if(loader.isLoaded(clazz)) res.add(loader);
		return res;
	}
	
	public boolean hasLoader(final Class<? extends Loader> cl) {
		return list.parallelStream().anyMatch(e->e.getClass().equals(cl));
	}
	
	@Override
	public boolean isLoaded(final Class<?> clazz) {
		return false;
	}
	
	@Override
	public Iterator<Loader> iterator() {
		return list.iterator();
	}
	
	@Override
	public Object load(final Class<?> clazz, final String value) {
		final double time = System.nanoTime();
		final Object r    = this.load0(clazz, value);
		Log.info(this + " load " + clazz + " " + value + " time(ms):" + ((System.nanoTime() - time) / 1_000_000) + " find in " + get(clazz));
		return r;
	}
	
	@Override
	public Object load(final String value) throws Exception {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <R> R load0(final Class<R> clazz, final String value) {
		final Collection<Exception> exception = new ArrayList<>();
		for(final Loader loader : get(clazz)) try {
			final R r = (R) loader.load(clazz, value);
			return r;
		}catch(final Exception e) {
			exception.add(e);
		}
		if(clazz.isPrimitive()) return null;
		for(final Exception e : exception) e.printStackTrace();
		throw new UnsupportedOperationException(String.format("%s as %s in %s %s", value, clazz.getName(), get(clazz), exception));
	}
	
}
