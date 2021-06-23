package com.greentree.engine.core.component;

import java.util.Random;

import com.greentree.engine.core.Events;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.event.Listener;

public abstract class GameComponent {

	private boolean isDestoy = false;

	private GameObject object;
	@Deprecated
	protected static final Random random = new Random();
	protected final static void addListener(final Listener listener) {
		Events.addListener(listener);
	}

	public static final <S extends GameSystem> void addSystem(final S system) {
		GameCore.addSystem(system);
	}

	protected final static GameObject createFromPrefab(final String prefab) {
		return GameCore.createFromPrefab(prefab);
	}

	public final boolean destroy() {
		if(isDestroy()) return true;
		isDestoy = true;
		object.removeComponent(this);
		object = null;
		return false;
	}

	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return getObject().getComponent(clazz);
	}

	public final GameObject getObject() {
		return object;
	}

	public final boolean isDestroy() {
		return isDestoy;
	}

	public final void setObject(final GameObject object) {//TODO
		this.object = object;
	}

}
