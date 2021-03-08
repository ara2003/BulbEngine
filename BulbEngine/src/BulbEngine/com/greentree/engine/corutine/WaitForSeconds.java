package com.greentree.engine.corutine;

import com.greentree.engine.Time;

public final class WaitForSeconds implements CustomResemInstruction {
	
	private final long seconds;
	private long time = 0;
	
	public WaitForSeconds(final float seconds) {
		this.seconds = (long) (seconds * Time.TimePerSecond);
	}
	
	@Override
	public boolean keepWaiting() {
		if(time == 0) time = Time.getTime() + seconds;
		if(time < Time.getTime()) {
			time = Time.getTime() + seconds;
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "WaitForSeconds [seconds=" + (seconds*1f / Time.TimePerSecond) + "]";
	}
}
