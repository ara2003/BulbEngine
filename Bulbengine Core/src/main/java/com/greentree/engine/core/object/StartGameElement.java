package com.greentree.engine.core.object;


public class StartGameElement extends GameElement {

	private boolean isStart = false;

	public StartGameElement() {
	}

	public void initSratr() {
		if(isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		isStart = true;
		start();
	}

	protected void start() {
	}

}
