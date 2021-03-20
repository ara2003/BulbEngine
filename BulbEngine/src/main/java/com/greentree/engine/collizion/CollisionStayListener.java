package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;

/** @author Arseny Latyshev */
@Deprecated
public interface CollisionStayListener extends CollisionListener {
	
	void CollisionStay(ColliderComponent collider);
}
