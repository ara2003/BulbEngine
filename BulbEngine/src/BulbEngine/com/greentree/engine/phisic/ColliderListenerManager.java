package com.greentree.engine.phisic;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerMutiEventListenerManager;

public class ColliderListenerManager extends OneListenerMutiEventListenerManager<ColliderListener> {
	
	private static final long serialVersionUID = 1L;
	
	public ColliderListenerManager() {
		super(ColliderListener.class, ColliderEvent.class);
	}
	
	@Override
	public void event0(final Event event) {
		if(event instanceof ColliderEvent) {
			final ColliderEvent colliderEvent = (ColliderEvent) event;
			switch(colliderEvent.getEventType()) {
				case enter:
					for(final ColliderListener lis : listeners)
						lis.CollisionEnter(colliderEvent.getObject1(), colliderEvent.getObject2());
				break;
				case exit:
					for(final ColliderListener lis : listeners)
						lis.CollisionExit(colliderEvent.getObject1(), colliderEvent.getObject2());
				break;
				case stay:
					for(final ColliderListener lis : listeners)
						lis.CollisionStay(colliderEvent.getObject1(), colliderEvent.getObject2());
				break;
			}
		}
	}
}
