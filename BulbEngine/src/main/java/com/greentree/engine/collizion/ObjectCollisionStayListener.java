package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameObject;

/** @author Arseny Latyshev */
@Deprecated
public interface ObjectCollisionStayListener extends CollisionStayListener {
	
	@Override
	default void CollisionStay(final ColliderComponent collider) {
		this.CollisionStay(collider.getObject());
	}
	
	void CollisionStay(GameObject object);
}
