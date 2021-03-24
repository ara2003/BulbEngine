package com.greentree.engine.system;

import java.util.Collections;
import java.util.Comparator;

import com.greentree.common.collection.DoubleSet;
import com.greentree.common.pair.UnOrentetPair;
import com.greentree.engine.Game;
import com.greentree.engine.collizion.CollisionEvent;
import com.greentree.engine.collizion.CollisionEvent.EventType;
import com.greentree.engine.component.ComponentList;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.object.GameSystem;

public class ColliderSystem extends GameSystem {
	
	private final DoubleSet<ColliderComponent> lastFream = new DoubleSet<>();
	private final DoubleSet<ColliderComponent> nowFream = new DoubleSet<>();
	
	@Override
	public void update() {
		ComponentList<ColliderComponent> colliderComponent = getAllComponentsAsComponentList(ColliderComponent.class);
		nowFream.clear();
		
		Collections.sort(colliderComponent, Comparator.comparing(e -> e.getX()));
		
		for(int i = 0; i < colliderComponent.size(); i++) {
			ColliderComponent a = (ColliderComponent) colliderComponent.toArray()[i];
			for(int j = i+1; j < colliderComponent.size(); j++) {
				ColliderComponent b = (ColliderComponent) colliderComponent.toArray()[j];
				if(a.getShape().isIntersect(b.getShape())) {
					nowFream.addPair(a, b);
				}else break;
			}
		}
		
		for(UnOrentetPair<ColliderComponent> p : nowFream) {
			if(lastFream.remove(p)) {
				Game.event(new CollisionEvent(EventType.stay, p.first, p.second));
			}else {
				Game.event(new CollisionEvent(EventType.enter, p.first, p.second));	
			}
		}
		for(UnOrentetPair<ColliderComponent> p : lastFream) {
			Game.event(new CollisionEvent(EventType.exit, p.first, p.second));
		}
		lastFream.clear();
		lastFream.addAll(nowFream);
	}


	
}