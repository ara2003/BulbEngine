package com.greentree.engine.object;

import com.greentree.engine.Game;
import com.greentree.engine.event.Listener;

/** @author Arseny Latyshev */
public abstract class GameElement {
	
	protected final static void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected final static GameObject createFromPrefab(final String prefab) {
		return Game.createFromPrefab(prefab);
	}
	
	protected void start() {
	}
	
	protected void update() {
	}
	
	private boolean isStart = false;
	
	public void initSratr() {
		if(this.isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		this.isStart = true;
		this.start();
	}
		
}
