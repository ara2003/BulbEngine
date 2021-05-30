package com.greentree.common.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Path<V> extends ArrayList<V> {
	private static final long serialVersionUID = 1L;
	private final Function<Joint<V>, Double> function;

	public Path(Collection<? extends V> arg0, Function<Joint<V>, Double> function) {
		super(arg0);
		this.function = function;
	}

	public List<Joint<V>> toJoints(){
		List<Joint<V>> res = new ArrayList<>();
		V v1 = get(0);
		for(int i = 1; i < size(); i++) {
			V v2 = get(i);
			res.add(new Joint<>(v1, v2));
			v1 = v2;
		}
		return res;
	}
	
	public double length() {
		return toJoints().parallelStream().mapToDouble(j -> function.apply(j)).sum();
	}



}
