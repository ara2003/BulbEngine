package com.greentree.data.parse;

import com.greentree.common.triple.Triple;

public interface TripleParser<V1, V2, V3, R> extends Parser<Triple<V1, V2, V3>, R> {
	
	@Override
	default R parse(Triple<V1, V2, V3> value) throws Exception {
		return parse(value.v1, value.v2, value.v3);
	}
	
	R parse(V1 v1, V2 v2, V3 v3) throws Exception;
	
	
}
