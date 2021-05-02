package com.greentree.engine.core.object;

import java.util.Random;

import com.greentree.engine.core.Events;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.corutine.Corutine;
import com.greentree.event.Listener;

public abstract class GameComponent extends GameElement {
	
	protected static final Random random = new Random();
	private GameObject object;
	
	protected final static void addListener(final Listener listener) {
		Events.addListener(listener);
	}
	
	protected final static GameObject createFromPrefab(final String prefab) {
		return GameCore.createFromPrefab(prefab);
	}
	
	public static final <S extends GameSystem> void addSystem(S system){
		GameCore.addSystem(system);
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return this.getObject().getComponent(clazz);
	}
	
	public final GameObject getObject() {
		return this.object;
	}
	
	public final boolean destroy() {
		if(super.destroy())return true;
		object.removeComponent(this);
		return false;
	}
	
	protected void start() {
	}
	
	protected final void startCorutine(final Corutine corutine) {
		this.getObject().startCorutine(corutine);
	}

	public void setObject(GameObject object) {
		this.object = object;
	}
	
}
