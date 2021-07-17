package com.greentree.engine2d.collizion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Table;
import com.greentree.action.EventAction;
import com.greentree.common.collection.DoubleSet;
import com.greentree.common.pair.UnOrentetPair;
import com.greentree.engine.Layers;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.engine.layer.Layer;

public class ColliderSystem extends MultiBehaviour {


	private final DoubleSet<ColliderComponent> lastFream = new DoubleSet<>();
	@Required
	@EditorData
	private Table<Layer, Layer, Boolean> table;
	private final EventAction<DoubleSet<ColliderComponent>> action = new EventAction<>();

	public EventAction<DoubleSet<ColliderComponent>> getAction() {
		return action;
	}
	private boolean layerIntersect(final ColliderComponent a, final ColliderComponent b) {
		if(table.contains(Layers.get(b.getObject()), Layers.get(a.getObject())))
			return table.get(Layers.get(b.getObject()), Layers.get(a.getObject()));
		if(table.contains(Layers.get(a.getObject()), Layers.get(b.getObject())))
			return table.get(Layers.get(a.getObject()), Layers.get(b.getObject()));
		return true;
	}

	@Override
	protected void start() {
	}

	@Override
	public void update() {
		final DoubleSet<ColliderComponent> nowFream = new DoubleSet<>();
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
		if(nowFream.isEmpty())return;
		action.action(nowFream);
		for(final UnOrentetPair<ColliderComponent> p : nowFream)
			if(lastFream.remove(p)) {
				p.first.getAction().collizionStay(p.seconde);
				p.seconde.getAction().collizionStay(p.first);
			}else {
				p.first.getAction().collizionEnter(p.seconde);
				p.seconde.getAction().collizionEnter(p.first);
			}
		for(final UnOrentetPair<ColliderComponent> p : lastFream) {
			p.first.getAction().collizionExit(p.seconde);
			p.seconde.getAction().collizionExit(p.first);
		}
		lastFream.clear();
		lastFream.addAll(nowFream);
	}
}
