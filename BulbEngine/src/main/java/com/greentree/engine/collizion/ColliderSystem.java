package com.greentree.engine.collizion;

import java.util.Collections;
import java.util.Comparator;

import com.google.common.collect.Table;
import com.greentree.common.collection.DoubleSet;
import com.greentree.engine.collizion.event.CollisionListEvent;
import com.greentree.engine.collizion.event.CollisionListenerManager;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.layer.Layer;
import com.greentree.engine.core.object.GameSystem;

public class ColliderSystem extends GameSystem {

	private final DoubleSet<ColliderComponent> nowFream = new DoubleSet<>();
	@EditorData(required = true)
	private Table<Layer, Layer, Boolean> table;

	private boolean layerIntersect(final ColliderComponent a, final ColliderComponent b) {
		if(table.contains(b.getObject().getLayer(), a.getObject().getLayer()))
			return table.get(b.getObject().getLayer(), a.getObject().getLayer());
		if(table.contains(a.getObject().getLayer(), b.getObject().getLayer()))
			return table.get(a.getObject().getLayer(), b.getObject().getLayer());
		return true;
	}

	@Override
	protected void start() {
		Events.getEventsystem().addListenerManager(new CollisionListenerManager());
	}

	@Override
	public void update() {
		final ComponentList<ColliderComponent> colliderComponent = this
				.getAllComponentsAsComponentList(ColliderComponent.class);
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
		Events.event(new CollisionListEvent(nowFream));
	}
}
