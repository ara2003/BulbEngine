package com.greentree.data.parse;

@Deprecated
public class ClassParserSet<V, R, L extends PairParser<Class<? extends R>, V, R>> extends PairParserSet<Class<? extends R>, V, R, L> implements PairParser<Class<? extends R>, V, R> {
	private static final long serialVersionUID = 1L;

//	@Override
//	protected final List<L> getCache(final Pair<V, Class<? extends R>> value) {
//		return getCache(value.second);
//	}
//	
//	protected List<L> getCache(Class<? extends R> clazz) {
//		return getList();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T extends R> T parse(final Class<T> clazz, final V v) throws Exception {
//		return (T) parse(new Pair<>(v, clazz));
//	}

	
	
}
