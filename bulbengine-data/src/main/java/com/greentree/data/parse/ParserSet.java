package com.greentree.data.parse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.greentree.common.logger.Log;
import com.greentree.common.pair.Pair;

/** @author Arseny Latyshev */
public class ParserSet<V, R, P extends Parser<V, R>> implements Parser<V, R>, Iterable<P>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Collection<P> loaders = new ArrayList<>();
	
	public void addParser(final P loader) {
		loaders.add(loader);
	}
	
	/**
	 * @return all loaders that can load this value
	 */
	protected Collection<P> getParsers(final V value) {
		return loaders;
	}

	protected Collection<P> getParsers() {
		return loaders;
	}
	
	/**
	 * @return can value be null
	 */
	protected boolean isNullable(final V value) {
		return false;
	}
	
	
	@Override
	public Iterator<P> iterator() {
		return loaders.iterator();
	}

	protected final R parse(final V value, Collection<P> collection) throws Exception {
		if(collection.isEmpty())throw new IllegalArgumentException("not parsers for " + value);
		final Collection<Pair<P, Exception>> exception = new ArrayList<>();
		for(final P p : collection) try {
			var res = p.parse(value);
			Log.info(value + " parse with " + p);
			return res;
		}catch(final Exception e) {
			exception.add(new Pair<>(p, e));
		}
		if(isNullable(value)) {
			return null;
		}else {
			for(final Pair<P, Exception> e : exception) {
				var e0 = new UnsupportedOperationException(String.format("%s in %s error:%s", value, e.first, e.seconde.getMessage()));
				e0.setStackTrace(e.seconde.getStackTrace());
				e0.printStackTrace();
			}
			throw new UnsupportedOperationException(exception.toString());
		}
	}
	
	@Override
	public final R parse(final V value) throws Exception {
		return parse(value, getParsers(value));
	}
	
	
}
