package com.greentree.engine.object;

import java.util.Random;

import com.greentree.engine.Game;
import com.greentree.engine.corutine.Corutine;
import com.greentree.event.Listener;

public abstract class GameComponent extends GameElement {
	
	protected static final Random random = new Random();
	private GameObject object;
	private boolean isAwake = false;
	
	protected final static void addListener(final Listener listener) {
		Game.addListener(listener);
	}
	
	protected final static GameObject createFromPrefab(final String prefab) {
		return Game.createFromPrefab(prefab);
	}
	
	@Deprecated
	protected void awake() {
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return this.getObject().getComponent(clazz);
	}

	public boolean isDestroy() {
		return object == null;
	}
	
	
	public GameObject getObject() {
		return this.object;
	}
	
	public void initAwake(final GameObject object) {
		if(this.isAwake) throw new UnsupportedOperationException("reinitialization of : " + this);
		this.isAwake = true;
		this.object  = object;
		this.awake();
	}
	
	@Override
	public final void initSratr() {
		if(!this.isAwake) throw new UnsupportedOperationException("start befor awake : " + this);
		super.initSratr();
	}
	
	protected final void startCorutine(final Corutine corutine) {
		this.getObject().startCorutine(corutine);
	}

	protected final void setObject(GameObject object) {
		this.object = object;
	}
}
