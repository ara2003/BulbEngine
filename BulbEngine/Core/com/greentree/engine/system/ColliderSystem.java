package com.greentree.engine.system;

import java.util.Set;

import com.greentree.engine.Game;
import com.greentree.engine.component.ColliderComponent;
import com.greentree.engine.phisic.ColliderEvent;
import com.greentree.engine.phisic.ColliderEvent.EventType;
import com.greentree.engine.system.util.GameSystem;

public class ColliderSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() {
		final Set<ColliderComponent> colliderComponent = getComponents(ColliderComponent.class);
		for(final ColliderComponent a : colliderComponent) for(final ColliderComponent b : colliderComponent) {
			if(a == b) break;
			if(a.getShape().isTouch(b.getShape()))
				Game.getEventSystem().event(new ColliderEvent(EventType.stay, a.getObject(), b.getObject()));
		}
	}
	
	@Override
	protected void start() {
	}
}
