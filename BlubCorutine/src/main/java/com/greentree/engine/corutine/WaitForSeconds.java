package com.greentree.engine.corutine;

import com.greentree.util.Time;

public final class WaitForSeconds implements CustomResemInstruction {
	
	private final double seconds;
	private double time = 0;
	
	public WaitForSeconds(final float seconds) {
		this.seconds = seconds;
	}
	
	@Override
	public boolean keepWaiting() {
		if(time == 0) time = Time.getTime() + seconds;
		if(time < Time.getTime()) {
			time = Time.getTime() + seconds;
			time = 0;
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "WaitForSeconds [seconds=" + (seconds) + "]";
	}
}
