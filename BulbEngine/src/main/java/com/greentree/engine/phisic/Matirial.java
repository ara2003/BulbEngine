package com.greentree.engine.phisic;

public final class Matirial {
	
	private float elasticity;
	
	public float getElasticity() {
		return this.elasticity;
	}
	
	@Override
	public String toString() {
		return "[Matirial [elasticity=" + this.elasticity + "]]";
	}
}
