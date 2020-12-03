package com.greentree.engine;

import java.util.HashMap;
import java.util.Map;

public final class Time {
	
	private static float delta;
	private static int fps, fps_;
	private static long lastFream, lastFPS, start;
	private static int fream;
	public static final long TimePerSecond = 1_000_000_000;
	private static final Map<String, Long> timers;
	static {
		Time.start = System.nanoTime();
		timers = new HashMap<>();
	}
	
	private Time() {
	}
	
	public static int finish(final int id) {
		final String name = Thread.currentThread().getStackTrace()[2].getClassName() + " "
				+ Thread.currentThread().getStackTrace()[2].getMethodName() + " " + id;
		final Long l = timers.remove(name);
		if(l == null) {
			Log.warn("In mothod " + name + " alive Timers are not found");
			return 0;
		}
		return (int) (Time.getTime() - l);
	}
	
	public static float getDelta() {
		return Time.delta;
	}
	
	public static int getFps() {
		return Time.fps;
	}

	public static int getFream() {
		return fream;
	}
	
	public static long getTime() {
		return System.nanoTime() - Time.start;
	}

	public static void start(final int id) {
		final String name = Thread.currentThread().getStackTrace()[2].getClassName() + " "
				+ Thread.currentThread().getStackTrace()[2].getMethodName() + " " + id;
		timers.put(name, getTime());
	}
	
	static void updata() {
		fream++;
		final long time = Time.getTime();
		Time.delta = (time - Time.lastFream) / (TimePerSecond/1000f);
		Time.lastFream = time;
		Time.fps_++;
		if(Time.lastFPS + TimePerSecond <= time) {
			Time.fps = Time.fps_;
			Time.fps_ = 0;
			Time.lastFPS = time;
		}
	}
}
