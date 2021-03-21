package com.greentree.engine.collizion;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.NecessarilyListenerManagers;

@NecessarilyListenerManagers({CollisionListenerManager.class})
public interface CollisionListener extends Listener {
	
	void CollisionEnter(final ColliderComponent object1, final ColliderComponent object2);
	void CollisionExit(final ColliderComponent object1, final ColliderComponent object2);
	void CollisionStay(final ColliderComponent object1, final ColliderComponent object2);

}
