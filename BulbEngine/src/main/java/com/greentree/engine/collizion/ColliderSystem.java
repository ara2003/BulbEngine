package com.greentree.engine.collizion;

import java.util.Collections;
import java.util.Comparator;

import com.greentree.common.collection.DoubleSet;
import com.greentree.common.pair.UnOrentetPair;
import com.greentree.engine.collizion.event.CollisionEvent;
import com.greentree.engine.collizion.event.CollisionEvent.EventType;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.GameSystem;
import com.greentree.engine.core.component.ComponentList;

public class ColliderSystem extends GameSystem {
	
	private final DoubleSet<ColliderComponent> lastFream = new DoubleSet<>();
	private final DoubleSet<ColliderComponent> nowFream = new DoubleSet<>();
	
	@Override
	public void update() {
		final ComponentList<ColliderComponent> colliderComponent = this.getAllComponentsAsComponentList(ColliderComponent.class);
		this.nowFream.clear();
		//
		Collections.sort(colliderComponent, Comparator.comparing(ColliderComponent::getX));
		for(int i = 0; i < colliderComponent.size(); i++) {
			final ColliderComponent a = (ColliderComponent) colliderComponent.toArray()[i];
			for(int j = i + 1; j < colliderComponent.size(); j++) {
				final ColliderComponent b = (ColliderComponent) colliderComponent.toArray()[j];
				if(a.isIntersect(b)) this.nowFream.addPair(a, b);
				else break;
			}
		}
		for(final UnOrentetPair<ColliderComponent> p : this.nowFream) if(this.lastFream.remove(p)) {
			p.first.getAction().collizionStay(p.second);
			p.second.getAction().collizionStay(p.first);
			Events.event(new CollisionEvent(EventType.STAY, p.first, p.second));
			Events.event(new CollisionEvent(EventType.STAY, p.second, p.first));
		}else {
			p.first.getAction().collizionEnter(p.second);
			p.second.getAction().collizionEnter(p.first);
			Events.event(new CollisionEvent(EventType.ENTER, p.first, p.second));
			Events.event(new CollisionEvent(EventType.ENTER, p.second, p.first));
		}
		for(final UnOrentetPair<ColliderComponent> p : this.lastFream) {
			p.first.getAction().collizionExit(p.second);
			p.second.getAction().collizionExit(p.first);
			Events.event(new CollisionEvent(EventType.EXIT, p.first, p.second));
			Events.event(new CollisionEvent(EventType.EXIT, p.second, p.first));
		}
		this.lastFream.clear();
		this.lastFream.addAll(this.nowFream);
	}
}
