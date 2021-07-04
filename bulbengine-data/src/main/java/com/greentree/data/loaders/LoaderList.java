package com.greentree.data.loaders;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.greentree.common.pair.Pair;
import com.greentree.common.xml.XMLElement;
import com.greentree.data.loaders.value.ValueLoader;
import com.greentree.data.parse.PairParserSet;

/** @author Arseny Latyshev */
public class LoaderList extends PairParserSet<Field, XMLElement, Object, Loader> {

	private static final long serialVersionUID = 1L;
	private final LoadingCache<Class<?>, List<Loader>> cache = CacheBuilder.newBuilder().softValues()
			.build(new CacheLoader<>() {

				@Override
				public List<Loader> load(final Class<?> key) throws Exception {
					return getParsers().parallelStream().filter(e->e.isLoaded(key)).collect(Collectors.toList());
				}
			});

	@Override
	public void addParser(final Loader loader) {
		if(loader instanceof SubLoader)
			((SubLoader) loader).setLoad((Function<Pair<Class<?>, String>, Object>) p->
			{
				for(var a : getCache(p.first).parallelStream().filter(e->e instanceof ValueLoader).map(e->(ValueLoader) e)
						.collect(Collectors.toList())) {
					try {
						return a.parse(p.first, p.second.trim());
					}catch(Exception e1) {
					}
				}
				throw new UnsupportedOperationException("no one loader can not load " + p);
			});
		super.addParser(loader);
	}
	
	private List<Loader> getCache(final Class<?> clazz) {
		try {
			return cache.get(clazz);
		}catch(final ExecutionException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	protected List<Loader> getParsers(final Pair<Field, XMLElement> value) {
		return getCache(value.first.getType());
	}

	@Override
	protected boolean isNullable(final Pair<Field, XMLElement> value) {
		return value.first.getType().isPrimitive();
	}
	
}
