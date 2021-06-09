package com.greentree.engine.core.object;

import java.util.Random;

import com.greentree.engine.core.Events;
import com.greentree.engine.core.GameCore;
import com.greentree.event.Listener;

public abstract class GameComponent extends GameElement {

	private GameObject object;
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

	@Override
	public final boolean destroy() {
		if(super.destroy()) return true;
		object.removeComponent(this);
		return false;
	}

	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return getObject().getComponent(clazz);
	}

	public final GameObject getObject() {
		return object;
	}

	public final void setObject(final GameObject object) {
		this.object = object;
	}

}
