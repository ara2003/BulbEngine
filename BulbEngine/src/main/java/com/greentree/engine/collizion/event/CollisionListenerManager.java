package com.greentree.engine.collizion.event;

import com.greentree.event.OneListenerListenerManager;

/** @author Arseny Latyshev */
public class CollisionListenerManager extends OneListenerListenerManager<CollisionEvent, DoubleCollisionListener> {
	
	private static final long serialVersionUID = 1L;
	
	public CollisionListenerManager() {
		super(DoubleCollisionListener.class);
	}
	
	@Override
	protected void event(final DoubleCollisionListener l, final CollisionEvent event) {
		switch(event.getEventType()) {
			case ENTER -> l.CollisionEnter(event.getCollider1(), event.getCollider2());
			case EXIT -> l.CollisionExit(event.getCollider1(), event.getCollider2());
			case STAY -> l.CollisionStay(event.getCollider1(), event.getCollider2());
		}
	}
}
