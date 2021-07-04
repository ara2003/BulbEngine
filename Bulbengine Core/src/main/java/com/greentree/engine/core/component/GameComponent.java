package com.greentree.engine.core.component;

import com.greentree.engine.core.object.GameObject;

public abstract class GameComponent {

	private boolean isDestoy = false;
	private GameObject object;
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
