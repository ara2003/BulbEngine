package com.greentree.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arseny Latyshev
 */
public class Timer {
	
	private final Map<Integer, Long> timers;

    public Timer() {
    	timers = new HashMap<>();
	}

	
	public void start(final int id) {
		timers.put(id, Time.getTime());
	}
	
	public long finish(final int id) {
		final Long l = timers.remove(id);
		if(l == null) {
			Log.warn("Timer " + id + " alive Timers are not found");
			return 0;
		}
		return Time.getTime() - l;
	}
	
	
}
