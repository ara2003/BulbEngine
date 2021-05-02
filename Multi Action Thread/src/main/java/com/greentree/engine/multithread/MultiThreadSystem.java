package com.greentree.engine.multithread;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arseny Latyshev
 *
 */
public class MultiThreadSystem {
	
	private final Map<String, ActionThread> map; 
	
	public MultiThreadSystem() {
		map = new HashMap<>();
	}
	
	protected void create(String name) {
		map.put(name, new ActionThread(name, 1 / 2f));
	}
	
	public void task(String name, Runnable task) {
		if(!map.containsKey(name))create(name);
		map.get(name).task(task);
	}

	public void destroy() {
		map.values().parallelStream().forEach(e -> e.destroy());
	}
	
}
