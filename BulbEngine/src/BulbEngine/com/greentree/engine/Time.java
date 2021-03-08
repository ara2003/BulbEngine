package com.greentree.engine;

public final class Time {
	
	private volatile static float delta;
	private volatile static int fps, fps_;
	private volatile static long lastFream, lastFPS, start;
	private volatile static int fream;
	public static final long TimePerSecond = 1_000_000_000;
	static {
		Time.start = System.nanoTime();
	}
	
	private Time() {
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
	
	static void updata() {
		fream++;
		final long time = Time.getTime();
		Time.delta = (time - Time.lastFream) * 1000f / TimePerSecond;
		Time.lastFream = time;
		Time.fps_++;
		if(Time.lastFPS + TimePerSecond <= time) {
			Time.fps = Time.fps_;
			Time.fps_ = 0;
			Time.lastFPS = time;
		}
	}
}
