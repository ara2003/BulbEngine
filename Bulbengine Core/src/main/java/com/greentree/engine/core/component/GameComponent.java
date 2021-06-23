package com.greentree.engine.core.component;

import com.greentree.engine.core.object.GameObject;

public abstract class GameComponent {

	private boolean isDestoy = false;
	private GameObject object;
//	private MonoBehaviour behaviour;
	
//	public GameComponent(GameObject object, MonoBehaviour behaviour) {
//		this.object = object;
//		this.behaviour = behaviour;
//	}

//	public MonoBehaviour getBehaviour() {
//		return behaviour;
//	}

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

	public abstract class MonoBehaviour {

		public final boolean destroy() {
			return GameComponent.this.destroy();
		}

		public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
			return object.getComponent(clazz);
		}

		public final GameObject getObject() {
			return object;
		}

		public final boolean isDestroy() {
			return isDestoy;
		}
	}

}
