package com.greentree.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arseny Latyshev
 *
 */
public class DoubleMap<T1, T2> {

	private Map<T1, T2> map1 = new HashMap<>();
	private Map<T2, T1> map2 = new HashMap<>();
	
    public T2 get1(T1 t){
    	return map1.get(t);
	}
    
    public T1 get2(T2 t){
    	return map2.get(t);
    }

	public void add(T1 t1, T2 t2) {
		map1.put(t1, t2);
		map2.put(t2, t1);
	}

}
