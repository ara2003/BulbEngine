package com.greentree.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArgumentList {

	private final HashSet<String> set = new HashSet<>();
	private final Map<String, String> conflict = new HashMap<>(), equivalent = new HashMap<>();

	public void add(String[] arguments) {
		set.addAll(Arrays.asList(arguments));
		for(String s : set) if(set.contains(conflict.get(s))) throw new RuntimeException("conflict arguments \"" + s + "\" \"" + conflict.get(s) + "\"");
		for(String s : set) {
			var r = equivalent.get(s);
			if(r != null)set.add(r);
		}
	}

	public void addConflict(String a, String b) {
		conflict.put(a, b);
		conflict.put(b, a);
	}
	public boolean hasArguments(String arg) {
		return set.contains(arg);
	}

	public void addEquivalent(String a, String b) {
		equivalent.put(a, b);
		equivalent.put(b, a);
	}
	
}
