package com.greentree.engine.phisic;

import com.greentree.engine.event.Listener;
import com.greentree.engine.object.GameObject;

public interface ColliderListener extends Listener {

	default void CollisionEnter(final GameObject object1, final GameObject object2) {
	}

	default void CollisionExit(final GameObject object1, final GameObject object2) {
	}

	default void CollisionStay(final GameObject object1, final GameObject object2) {
	}
}
