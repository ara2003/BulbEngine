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
			for(final Listener lis : this.listeners) if(lis instanceof CollisionListener) switch(collisionEvent.getEventType()) {
				case enter:
					((CollisionListener) lis).CollisionEnter(collisionEvent.getObject1(), collisionEvent.getObject2());
					((CollisionListener) lis).CollisionEnter(collisionEvent.getObject2(), collisionEvent.getObject1());
				break;
				case exit:
					((CollisionListener) lis).CollisionExit(collisionEvent.getObject1(), collisionEvent.getObject2());
					((CollisionListener) lis).CollisionExit(collisionEvent.getObject2(), collisionEvent.getObject1());
				break;
				case stay:
					((CollisionListener) lis).CollisionStay(collisionEvent.getObject1(), collisionEvent.getObject2());
					((CollisionListener) lis).CollisionStay(collisionEvent.getObject2(), collisionEvent.getObject1());
				break;
			}
		}
	}
}
