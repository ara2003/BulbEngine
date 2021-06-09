package com.greentree.engine.collizion.event;

import com.greentree.common.collection.DoubleSet;
import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.event.Listener;
import com.greentree.event.NecessarilyListenerManagers;

@NecessarilyListenerManagers({CollisionListenerManager.class})
public interface CollisionListListener extends Listener {

	void ColliderList(DoubleSet<ColliderComponent> list);

}
