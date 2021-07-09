package com.greentree.engine.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArgumentList {

	private final HashSet<String> set = new HashSet<>();
	private final Map<String, String> conflict = new HashMap<>();

	public void add(String[] arguments) {
		var list = Arrays.asList(arguments);
		for(String s : list) if(list.contains(conflict.get(s))) throw new RuntimeException("conflict arguments \"" + s + "\" \"" + conflict.get(s) + "\"");
		set.addAll(list);
	}

	public void addConflict(String a, String b) {
		conflict.put(a, b);
		conflict.put(b, a);
	}

	public boolean hasArguments(String arg) {
		return set.contains(arg);
	}

}
