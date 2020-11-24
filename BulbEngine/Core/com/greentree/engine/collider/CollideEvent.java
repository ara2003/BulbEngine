package com.greentree.engine.collider;

import com.greentree.engine.object.GameObject;

public final class CollideEvent {

	final GameObject a, b;

	public CollideEvent(final GameObject gameObject, final GameObject gameObject2) {
		a = gameObject;
		b = gameObject2;
	}

	public GameObject getCollider1() {
		return a;
	}

	public GameObject getCollider2() {
		return b;
	}
}
