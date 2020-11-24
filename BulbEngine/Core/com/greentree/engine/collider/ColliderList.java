package com.greentree.engine.collider;

import java.util.HashMap;
import java.util.Map;

import com.greentree.engine.component.CircleColliderComponent;
import com.greentree.engine.event.EventSystem;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.object.GameObjectListener;

public final class ColliderList {
	
	private final Map<Collider, GameObject> colliders;

	public ColliderList(final EventSystem eventSystem) {
		colliders = new HashMap<>();
		eventSystem.addListener(new GameObjectListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void destroy(final GameObject gameObject) {
				final CircleColliderComponent c = gameObject.getComponent(CircleColliderComponent.class);
				if(c != null) colliders.remove(c.collider);
			}
		});
	}
	
	public void addCollider(final Collider collider, final GameObject obj) {
		colliders.put(collider, obj);
	}

	public void update() {
		for(final Collider a : colliders.keySet()) for(final Collider b : colliders.keySet()) {
			if(a == b) break;
			if(a.contact(b)) {
				final GameObject o1 = colliders.get(a);
				final GameObject o2 = colliders.get(b);
				o1.CollideEvent(o2);
				o2.CollideEvent(o1);
			}
		}
	}
}
