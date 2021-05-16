package com.greentree.data.parse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.greentree.common.pair.Pair;

/** @author Arseny Latyshev */
public class ParserSet<V, R, L extends Parser<V, R>> implements Parser<V, R>, Iterable<L>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Collection<L> list = new ArrayList<>();
	
	public void addLoader(final L loader) {
		list.add(loader);
	}
	
	
	protected Collection<L> getCache(final V value) {
		return list;
	}
	
	protected Collection<L> getList() {
		return list;
	}
	
	public boolean hasParser(final Class<? extends L> clazz) {
		return list.parallelStream().anyMatch(l->l.getClass().equals(clazz));
	}
	
	protected boolean isNullable(final V value) {
		return false;
	}
	
	
	@Override
	public Iterator<L> iterator() {
		return list.iterator();
	}

	public final R parse(final V value, Collection<L> collection) throws Exception {
		final Collection<Pair<L, Exception>> exception = new ArrayList<>();
		for(final L a : collection) try {
			return a.parse(value);
		}catch(final Exception e) {
			exception.add(new Pair<>(a, e));
		}
		if(isNullable(value)) {
			return null;
		}else {
			for(final Pair<L, Exception> e : exception) {
				var e0 = new UnsupportedOperationException(String.format("%s in %s error:%s", value, e.first, e.second.getMessage()));
				e0.setStackTrace(e.second.getStackTrace());
				e0.printStackTrace();
			}
			throw new UnsupportedOperationException(exception.toString());
		}
	}
	
	@Override
	public final R parse(final V value) throws Exception {
		return parse(value, getCache(value));
	}
	
	
}
