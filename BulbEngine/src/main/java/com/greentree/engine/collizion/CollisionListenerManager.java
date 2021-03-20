package com.greentree.engine.collizion;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.MutiListenerListenerManager;

public class CollisionListenerManager extends MutiListenerListenerManager {
	
	private static final long serialVersionUID = 1L;
	
	public CollisionListenerManager() {
		super(CollisionListener.class);
	}
	
	@Override
	public void event(final Event event) {
		if(event instanceof CollisionEvent) {
			final CollisionEvent collisionEvent = (CollisionEvent) event;
			for(final Listener lis : this.listeners)
				if(lis instanceof SimpleCollisionListener) switch(collisionEvent.getEventType()) {
				case enter:
				((SimpleCollisionListener) lis).CollisionEnter(collisionEvent.getObject1(),
						collisionEvent.getObject2());
				break;
				case exit:
				((SimpleCollisionListener) lis).CollisionExit(collisionEvent.getObject1(), collisionEvent.getObject2());
				break;
				case stay:
				((SimpleCollisionListener) lis).CollisionStay(collisionEvent.getObject1(), collisionEvent.getObject2());
				break;
				}
		}
	}
}
