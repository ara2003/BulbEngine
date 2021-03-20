package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
@Deprecated
public interface ObjectCollisionEnterListener extends CollisionEnterListener {
	
	@Override
	default void CollisionEnter(final ColliderComponent collider) {
		this.CollisionEnter(collider.getObject());
	}
	
	void CollisionEnter(GameObject object);
}
