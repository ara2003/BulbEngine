package com.greentree.data.parse;

import com.greentree.common.pair.Pair;

/** @author Arseny Latyshev */
public abstract class PairParserSet<V1, V2, R, L extends PairParser<V1, V2, R>> extends ParserSet<Pair<V1, V2>, R, L> implements PairParser<V1, V2, R> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public R parse(V1 value1, V2 value2) throws Exception {
		return parse(new Pair<>(value1, value2));
	}
	
}
