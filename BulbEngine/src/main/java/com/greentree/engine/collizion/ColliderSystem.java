package com.greentree.engine.collizion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Table;
import com.greentree.common.collection.DoubleSet;
import com.greentree.common.logger.Log;
import com.greentree.engine.Layers;
import com.greentree.engine.collizion.event.CollisionListEvent;
import com.greentree.engine.collizion.event.CollisionListenerManager;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.core.util.Events;
import com.greentree.engine.layer.Layer;

public class ColliderSystem extends MultiBehaviour {

	private final DoubleSet<ColliderComponent> nowFream = new DoubleSet<>();
	@Required
	@EditorData
	private Table<Layer, Layer, Boolean> table;

	private boolean layerIntersect(final ColliderComponent a, final ColliderComponent b) {
		if(table.contains(Layers.get(b.getObject()), Layers.get(a.getObject())))
			return table.get(Layers.get(b.getObject()), Layers.get(a.getObject()));
		if(table.contains(Layers.get(a.getObject()), Layers.get(b.getObject())))
			return table.get(Layers.get(a.getObject()), Layers.get(b.getObject()));
		return true;
	}

	@Override
	protected void start() {
		Events.getEventsystem().addListenerManager(new CollisionListenerManager());
	}

	@Override
	public void update() {
		final List<ColliderComponent> colliderComponent = getAllComponents(ColliderComponent.class);
		nowFream.clear();
		Collections.sort(colliderComponent, Comparator.comparing(ColliderComponent::getX));
		for(int i = 0; i < colliderComponent.size(); i++) {
			final ColliderComponent a = (ColliderComponent) colliderComponent.toArray()[i];
			for(int j = i + 1; j < colliderComponent.size(); j++) {
				final ColliderComponent b = (ColliderComponent) colliderComponent.toArray()[j];
				if(!layerIntersect(a, b)) continue;
				if(a.isIntersect(b)) nowFream.addPair(a, b);
				else break;
			}
		}
		if(!nowFream.isEmpty()) {
    		Events.event(new CollisionListEvent(nowFream));
		}
	}
}
