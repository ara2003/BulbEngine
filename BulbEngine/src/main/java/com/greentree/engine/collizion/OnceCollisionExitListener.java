package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;

/** @author Arseny Latyshev */
@Deprecated
public interface OnceCollisionExitListener extends OnceCollisionListener {
	
	void CollisionExit(ColliderComponent collider);
}
