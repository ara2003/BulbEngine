package com.greentree.engine.object;

import java.io.Serializable;
import java.util.Random;

import com.greentree.engine.Game;
import com.greentree.engine.event.Listener;
import com.greentree.util.Log;

/** @author Arseny Latyshev */
public abstract class GameElement implements Serializable {
	
	protected static final long serialVersionUID = 1L;
	protected static final Random random = new Random();
	private boolean initialize;
	
	protected final static void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected GameObject createObject(final String prefab) {
		return Game.getCurrentScene().createObject(prefab);
	}
	
	public final void init() {
		if(this.initialize) Log.error(new IllegalAccessError("seconde start of " + this));
		this.initialize = true;
		start();
	}
	
	protected final boolean isInitialized() {
		return this.initialize;
	}
	
	protected abstract void start();
	
	@Override
	public String toString() {
		return "GameElement [initialize=" + this.isInitialized() + "]";
	}
	
	protected abstract void update();
}
