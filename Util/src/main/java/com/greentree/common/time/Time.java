package com.greentree.common.time;

public final class Time {
	
	private volatile static double delta;
	private volatile static int fps, fps_;
	private volatile static double lastFream, lastFPS = 0, start;
	private static final long TimePerSecond = 1_000_000_000;
	static {
		Time.start = System.nanoTime();
	}
	
	private Time() {
	}

	public static double getAbsolutDelta() {
		return Time.delta;
	}
	public static float getDelta() {
		return (float)Time.delta;
	}
	
	public static int getFps() {
		return Time.fps;
	}

	public static float getTime() {
		return (float) (getAbsolutTime());
	}

	private static double getAbsolutTime() {
		return (System.nanoTime() - Time.start) / TimePerSecond;
	}
	
	public static void updata() {
		final double time = Time.getAbsolutTime();
		Time.delta = time - Time.lastFream;
		Time.lastFream = time;
		Time.fps_++;
		if(Time.lastFPS + .5f <= time) {
			Time.fps = Time.fps_ * 2;
			Time.fps_ = 0;
			Time.lastFPS = time;
		}
	}
}
