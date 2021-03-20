package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
public interface OnceObjectCollisionListener extends OnceCollisionListener, SimpleCollisionListener {
	
	@Override
	default void CollisionEnter(final ColliderComponent object) {
		this.CollisionEnter(object.getObject());
	}
	
	void CollisionEnter(GameObject object);
	
	@Override
	default void CollisionExit(final ColliderComponent object) {
		this.CollisionExit(object.getObject());
	}
	
	void CollisionExit(GameObject object);
	
	@Override
	default void CollisionStay(final ColliderComponent object) {
		this.CollisionStay(object.getObject());
	}
	
	void CollisionStay(GameObject object);
}
