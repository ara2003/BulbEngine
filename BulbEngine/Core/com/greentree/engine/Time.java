package com.greentree.engine;

public final class Time {
	
	private static float delta;
	private static int fps, fps_;
	private static long lastFream, lastFPS, start;
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
	
	public static long getTime() {
		return System.nanoTime() - Time.start;
	}
	
	static void updata() {
		final long time = Time.getTime();
		Time.delta = (time - Time.lastFream) / 1000000f;
		Time.lastFream = time;
		Time.fps_++;
		if(Time.lastFPS + 1000000000 <= time) {
			Time.fps = Time.fps_;
			Time.fps_ = 0;
			Time.lastFPS = time;
		}
	}
}
