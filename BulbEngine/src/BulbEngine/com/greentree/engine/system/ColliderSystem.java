package com.greentree.engine.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.greentree.engine.Game;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.phisic.ColliderEvent;
import com.greentree.engine.phisic.ColliderEvent.EventType;

public class ColliderSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	private final Map<ColliderComponent, ColliderComponent> historyLastFream = new HashMap<>();
	
	@Override
	public void execute() {
		final Collection<ColliderComponent> colliderComponent = getComponents(ColliderComponent.class);
		for(final ColliderComponent a : colliderComponent) {
			for(final ColliderComponent b : colliderComponent) {
				if(a == b) break;//break to process one pair once
				if(a.getShape().isIntersect(b.getShape())) {//collision
					if(!historyLastFream.remove(a, b) && !historyLastFream.remove(b, a))
						Game.event(new ColliderEvent(EventType.enter, a, b));
					Game.event(new ColliderEvent(EventType.stay, a, b));
					historyLastFream.put(a, b);
					historyLastFream.put(b, a);
				}else {//not collision
					historyLastFream.remove(b, a);
					if(historyLastFream.remove(a, b)) {
						Game.event(new ColliderEvent(EventType.exit, a, b));
					}
				}
			}
		}
	}
	
	@Override
	protected void start() {
	}
}
