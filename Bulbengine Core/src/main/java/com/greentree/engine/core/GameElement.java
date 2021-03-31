package com.greentree.engine.core;

/** @author Arseny Latyshev */
public abstract class GameElement {
	
	private boolean isStart = false;
	
	public void initSratr() {
		if(this.isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		this.isStart = true;
		this.start();
	}
	
	protected void start() {
	}
	
	protected void update() {
	}
}
