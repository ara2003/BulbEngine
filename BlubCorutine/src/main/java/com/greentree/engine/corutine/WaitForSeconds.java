package com.greentree.engine.corutine;

import java.util.concurrent.TimeUnit;

public final class WaitForSeconds implements CustomResemInstruction {
	
	private final long nanoSeconds;
	private long time = 0;

	public WaitForSeconds(final double time, TimeUnit type) {
		nanoSeconds = type.toNanos((long)time);
	}

	public WaitForSeconds(final double seconds) {
		this(seconds, TimeUnit.SECONDS);
	}
	
	@Override
	public boolean keepWaiting() {
		if(time == 0) time = System.nanoTime() + nanoSeconds;
		if(System.nanoTime() > time) {
			time = 0;
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "WaitForSeconds [seconds=" + TimeUnit.NANOSECONDS.toSeconds(nanoSeconds) + "]";
	}
}
