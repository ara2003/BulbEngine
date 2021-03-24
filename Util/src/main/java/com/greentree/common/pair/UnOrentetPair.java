package com.greentree.common.pair;


/**
 * @author Arseny Latyshev
 *
 */
public class UnOrentetPair<T> extends Pair<T, T> {

	public UnOrentetPair() {
		super();
	}
	public UnOrentetPair(T first, T second) {
		super(first, second);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof UnOrentetPair)) return false;
		UnOrentetPair other = (UnOrentetPair) obj;
		if(first.equals(other.first)){
			return second.equals(other.second);
		}else 
		if(second.equals(other.second)){
			return first.equals(other.first);
		}else 
		if(first.equals(other.second)){
			return second.equals(other.first);
		}else 
		if(second.equals(other.first)){
			return first.equals(other.second);
		}else
		return false;
	}
	
	public UnOrentetPair<T> reset(T e1, T e2) {
		first = e1;
		second = e2;
		return this;
	}
	
}
