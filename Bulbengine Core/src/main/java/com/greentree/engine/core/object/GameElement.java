package com.greentree.engine.core.object;

/** @author Arseny Latyshev */
public abstract class GameElement {

	private boolean isStart = false;
	private boolean isDestoy = false;
	
	public GameElement() {
	}
	
	public void initSratr() {
		if(this.isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		this.isStart = true;
		this.start();
	}

	protected void start() {
	}
	
	protected boolean destroy() {
		if(this.isDestroy()) //	throw new RuntimeException("destroy desroed object");
			return true;
		isDestoy = true;
		return false;
	}

	public final boolean isDestroy() {
		return isDestoy;
	}
	
}
