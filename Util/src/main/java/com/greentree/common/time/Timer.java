package com.greentree.common.time;

import java.util.HashMap;
import java.util.Map;

import com.greentree.common.Log;

/** @author Arseny Latyshev */
public class Timer {
	
	private final Map<Integer, Float> timers;
	
	public Timer() {
		timers = new HashMap<>();
	}
	
	public float finish(final int id) {
		final Float l = timers.remove(id);
		if(l == null) {
			Log.warn("Timer " + id + " alive Timers are not found");
			return 0;
		}
		return Time.getTime() - l;
	}
	
	public void start(final int id) {
		timers.put(id, Time.getTime());
	}
}
