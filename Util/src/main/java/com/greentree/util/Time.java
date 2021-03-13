package com.greentree.util;

public final class Time {
	
	private volatile static float delta;
	private volatile static int fps, fps_;
	private volatile static float lastFream, lastFPS, start;
	private static final long TimePerSecond = 1_000_000_000;
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
	
	public static float getTime() {
		return (System.nanoTime() - Time.start) / TimePerSecond;
	}
	
	public static void updata() {
		final float time = Time.getTime();
		Time.delta = time - Time.lastFream;
		Time.lastFream = time;
		Time.fps_++;
		if(Time.lastFPS + 1 <= time) {
			Time.fps = Time.fps_;
			Time.fps_ = 0;
			Time.lastFPS = time;
		}
	}
}
