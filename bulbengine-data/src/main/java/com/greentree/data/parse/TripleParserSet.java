package com.greentree.data.parse;

import com.greentree.common.triple.Triple;

/** @author Arseny Latyshev */
public abstract class TripleParserSet<V1, V2, V3, R, L extends TripleParser<V1, V2, V3, R>> extends ParserSet<Triple<V1, V2, V3>, R, L> implements TripleParser<V1, V2, V3, R> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public R parse(V1 value1, V2 value2, V3 value3) throws Exception {
		return parse(new Triple<>(value1, value2, value3));
	}
	
}
