package com.greentree.util.math;

public class FastTrig {
	
	private FastTrig() {
	}
	
	public static double cos(final double radians) {
		return FastTrig.sin(radians + Math.PI / 2);
	}
	
	private static double reduceSinAngle(double radians) {
		radians %= Math.PI * 2;
		if(Math.abs(radians) > Math.PI) radians -= Math.PI * 2;
		if(Math.abs(radians) > Math.PI / 2) radians = Math.PI - radians;
		return radians;
	}
	
	public static double sin(double radians) {
		radians = FastTrig.reduceSinAngle(radians);
		if(Math.abs(radians) <= Math.PI / 4) return Math.sin(radians);
		return Math.cos(Math.PI / 2 - radians);
	}
}
