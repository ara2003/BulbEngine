package com.greentree.data.parse;

import com.greentree.common.pair.Pair;


/**
 * @author Arseny Latyshev
 *
 */
public interface PairParser<V1, V2, R> extends Parser<Pair<V1, V2>, R> { 
	
	@Override
	default R parse(Pair<V1, V2> value) throws Exception {
		return parse(value.first, value.seconde);
	}
	
	R parse(V1 value1, V2 value2) throws Exception;
	
}
