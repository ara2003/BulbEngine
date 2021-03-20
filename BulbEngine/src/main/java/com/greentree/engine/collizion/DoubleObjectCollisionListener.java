package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
public interface DoubleObjectCollisionListener extends SimpleCollisionListener, ObjectCollisionListener {
	
	@Override
	default void CollisionEnter(final ColliderComponent object1, final ColliderComponent object2) {
		this.CollisionEnter(object1.getObject(), object2.getObject());
	}
	
	void CollisionEnter(GameObject object, GameObject object2);
	
	@Override
	default void CollisionExit(final ColliderComponent object1, final ColliderComponent object2) {
		this.CollisionExit(object1.getObject(), object2.getObject());
	}
	
	void CollisionExit(GameObject object, GameObject object2);
	
	@Override
	default void CollisionStay(final ColliderComponent object1, final ColliderComponent object2) {
		this.CollisionStay(object1.getObject(), object2.getObject());
	}
	
	void CollisionStay(GameObject object, GameObject object2);
}
