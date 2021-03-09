package com.greentree.engine.phisic;

import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;

@necessarilyListenerManagers({ColliderListenerManager.class})
public interface ColliderListener extends Listener {
	
	default void CollisionEnter(final ColliderComponent object1, final ColliderComponent object2) {
	}
	
	default void CollisionExit(final ColliderComponent object1, final ColliderComponent object2) {
	}
	
	default void CollisionStay(final ColliderComponent object1, final ColliderComponent object2) {
	}
}
