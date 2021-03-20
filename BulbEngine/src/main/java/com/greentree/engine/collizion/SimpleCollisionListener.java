package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;

public interface SimpleCollisionListener extends CollisionListener {
	
	void CollisionEnter(final ColliderComponent object1, final ColliderComponent object2);
	void CollisionExit(final ColliderComponent object1, final ColliderComponent object2);
	void CollisionStay(final ColliderComponent object1, final ColliderComponent object2);
}
