package com.greentree.engine.system;

import com.greentree.engine.core.node.GameSystem.MultiBehaviour;


public class TimeSystem extends MultiBehaviour {

	private volatile float delta;
	private volatile int fps, fps_;
	private volatile float lastFream, lastFPS = 0, start = System.nanoTime();
	private final static long TimePerSecond = 1_000_000_000;

	public float getDelta() {
		return delta;
	}

	public int getFps() {
		return fps;
	}

	public float getTime() {
		return (float) ((System.nanoTime() - start) / TimePerSecond);
	}

	@Override
	public void update() {
		final float time = getTime();
		delta = time - lastFream;
		lastFream = time;
		fps_++;
		if(lastFPS + .5f <= time) {
			fps = fps_ * 2;
			fps_ = 0;
			lastFPS = time;
		}
	}

}
