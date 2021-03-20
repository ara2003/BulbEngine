package com.greentree.engine.collizion;

import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
public interface OnceObjectCollisionAdapter extends OnceObjectCollisionListener {
	
	@Override
	default void CollisionEnter(final GameObject object) {
	}
	
	@Override
	default void CollisionExit(final GameObject object) {
	}
	
	@Override
	default void CollisionStay(final GameObject object) {
	}
}
