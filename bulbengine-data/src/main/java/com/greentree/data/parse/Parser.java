package com.greentree.data.parse;


/** @author Arseny Latyshev */
public interface Parser<V, R> {
	
	R parse(V value) throws Exception;
	
}
