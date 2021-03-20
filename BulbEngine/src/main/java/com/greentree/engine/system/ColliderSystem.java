package com.greentree.engine.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.greentree.engine.Game;
import com.greentree.engine.collizion.CollisionEvent;
import com.greentree.engine.collizion.CollisionEvent.EventType;
import com.greentree.engine.component.collider.ColliderComponent;

public class ColliderSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	private final Map<ColliderComponent, ColliderComponent> historyLastFream = new HashMap<>();
	
	@Override
	public void update() {
		final Collection<ColliderComponent> colliderComponent = getAllComponentsAsComponentList(ColliderComponent.class);
		for(final ColliderComponent a : colliderComponent) {
			for(final ColliderComponent b : colliderComponent) {
				if(a == b) break;//break to process one pair once
				if(a.getShape().isIntersect(b.getShape())) {//collision
					if(!historyLastFream.remove(a, b) && !historyLastFream.remove(b, a))
						Game.event(new CollisionEvent(EventType.enter, a, b));
					Game.event(new CollisionEvent(EventType.stay, a, b));
					historyLastFream.put(a, b);
					historyLastFream.put(b, a);
				}else {//not collision
					historyLastFream.remove(b, a);
					if(historyLastFream.remove(a, b)) {
						Game.event(new CollisionEvent(EventType.exit, a, b));
					}
				}
			}
		}
	}
	
}
