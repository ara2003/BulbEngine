package com.greentree.engine.collizion.event;

import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.event.Listener;
import com.greentree.event.NecessarilyListenerManagers;

@NecessarilyListenerManagers({CollisionListenerManager.class})
public interface DoubleCollisionListener extends Listener {
	
	void CollisionEnter(final ColliderComponent object1, final ColliderComponent object2);
	void CollisionExit(final ColliderComponent object1, final ColliderComponent object2);
	void CollisionStay(final ColliderComponent object1, final ColliderComponent object2);
	
}
