package com.greentree.engine.core;

import java.util.Random;

import com.greentree.engine.corutine.Corutine;
import com.greentree.event.Listener;

public abstract class GameComponent {
	
	protected static final Random random = new Random();
	private GameObject object;
	/** @deprecated use start */
	@Deprecated
	private boolean isAwake = false;
	private boolean isStart = false;
	
	protected final static void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected final static GameObject createFromPrefab(final String prefab) {
		return Game.createFromPrefab(prefab);
	}
	
	/** @deprecated use start */
	@Deprecated
	protected void awake() {
	}
	
	public static final <S extends GameSystem> void addSystem(S system){
		Game.addSystem(system);
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return this.getObject().getComponent(clazz);
	}
	
	public final GameObject getObject() {
		return this.object;
	}
	
	/** @deprecated use start */
	@Deprecated
	public final void initAwake() {
		if(this.isAwake) throw new UnsupportedOperationException("reinitialization of : " + this);
		this.isAwake = true;
		this.awake();
	}
	
	public final void initSratr() {
		if(!this.isAwake) throw new UnsupportedOperationException("start befor awake : " + this);
		if(this.isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		this.isStart = true;
		this.start();
	}
	
	public boolean isDestroy() {
		return this.object == null;
	}
	
	protected final void setObject(final GameObject object) {
		this.object = object;
	}
	
	protected void start() {
	}
	
	protected final void startCorutine(final Corutine corutine) {
		this.getObject().startCorutine(corutine);
	}
}
