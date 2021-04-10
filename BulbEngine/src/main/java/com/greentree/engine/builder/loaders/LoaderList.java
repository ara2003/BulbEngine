package com.greentree.engine.builder.loaders;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.greentree.common.logger.Log;

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
	
	private Collection<Loader> get(final Class<?> clazz) {
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
	
	public <R> R load(final Class<R> clazz, final String value) {
		final double time = System.nanoTime();
		final R      r    = this.load0(clazz, value);
		Log.info(this + " load " + clazz + " " + value + " time(ms):" + (System.nanoTime() - time) / 1_000_000 + " find in " + this.get(clazz));
		return r;
	}
	
	private <R> R load0(Class<R> clazz, String value) {
		boolean multyThread = false;
		//TODO process isMultyThread;
		return (multyThread) ? loadMultiThread0(clazz, value) : loadOneThread(clazz, value);
	}

	@SuppressWarnings("unchecked")
	private <R> R loadMultiThread0(final Class<R> clazz, final String value) {
		final Collection<Future<?>> futures    = new ArrayList<>();
		final Collection<Throwable> exception  = new ArrayList<>();
		final ExecutorService       threadPool = Executors.newFixedThreadPool(8);
		for(final Loader loader : this.get(clazz)) try {
			futures.add(threadPool.submit(()->loader.load(clazz, value)));
		}catch(final Exception e) {
			exception.add(e);
		}
		threadPool.shutdown();
		for(final Future<?> future : futures)
			try {
				final Object res = future.get();
				if(res != null) return (R) res;
			}catch(final ExecutionException e) {
				exception.add(e.getCause());
			}catch(final InterruptedException e) {
				exception.add(e);
			}
		if(clazz.isPrimitive()) return null;
		
		
		String str = "";
		for(final Throwable e : exception) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			str += sw.toString();
		}
		throw new UnsupportedOperationException(String.format("%s %s as %s in %s %s", str, value, clazz.getName(), this.get(clazz), exception));
	}
	
	@SuppressWarnings("unchecked")
	private <R> R loadOneThread(final Class<R> clazz, final String value) {
		final Collection<Exception> exception = new ArrayList<>();
		for(final Loader loader : this.get(clazz)) try {
			final R r = (R) loader.load(clazz, value);
			return r;
		}catch(final Exception e) {
			exception.add(e);
		}
		if(clazz.isPrimitive()) return null;
		for(final Exception e : exception) e.printStackTrace();
		throw new UnsupportedOperationException(String.format("%s as %s in %s %s", value, clazz.getName(), this.get(clazz), exception));
	}
}
