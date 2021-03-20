package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;

/** @author Arseny Latyshev */
@Deprecated
public interface CollisionEnterListener extends CollisionListener {
	
	void CollisionEnter(ColliderComponent collider);
}
