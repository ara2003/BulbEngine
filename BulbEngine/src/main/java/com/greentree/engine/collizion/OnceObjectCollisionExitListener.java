package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
@Deprecated
public interface OnceObjectCollisionExitListener extends OnceCollisionExitListener {
	
	@Override
	default void CollisionExit(final ColliderComponent collider) {
		this.CollisionExit(collider.getObject());
	}
	
	void CollisionExit(GameObject object);
}
