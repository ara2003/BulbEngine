package com.greentree.data.asset;

import java.util.HashMap;
import java.util.Map;

public class AssetSubTybeTable {
	
	private final Map<String, Integer> map = new HashMap<>();
	
	public void add(String type, int id) {
		map.put(type, id);
	}
	
	public int get(String type) {
		Integer var = map.get(type);
		if(var == null)throw new IllegalArgumentException("type " + type + " is not registered");
		return var;
	}
	
}
