package com.greentree.engine.core.object;

/** @author Arseny Latyshev */
public abstract class GameElement {
	
	private boolean isStart = false;
	private boolean isDestoy = false;
	
	public GameElement() {
	}
	
	protected boolean destroy() {
		if(isDestroy()) //	throw new RuntimeException("destroy desroed object");
			return true;
		isDestoy = true;
		return false;
	}
	
	public void initSratr() {
		if(isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		isStart = true;
		start();
	}
	
	public final boolean isDestroy() {
		return isDestoy;
	}
	
	protected void start() {
	}
	
}
