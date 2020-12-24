package com.greentree.engine.script;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arseny Latyshev
 *
 */
public class ScriptContext {

	private Map<String, Value> map;
	private Map<String, Type> types;
	
	public ScriptContext(){
		map = new HashMap<>();
		types = new HashMap<>();
	}
	
	public Type getType(String name) {
		return types.get(name);
	}
	
	public void add(Type type, String name) {
		map.put(name, new Value(type));
	}
	
	@Override
	protected ScriptContext clone() {
		return new ScriptContext();
	}
	
	public void addType(String name, Type type) {
		types.put(name, type);
	}
	
}
