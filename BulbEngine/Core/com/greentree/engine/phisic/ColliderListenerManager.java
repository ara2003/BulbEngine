package com.greentree.engine.phisic;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.ListenerManager;

public class ColliderListenerManager extends ListenerManager {
	
	private static final long serialVersionUID = 1L;
	private final List<ColliderListener> listeners = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public ColliderListenerManager() {
		super(ColliderEvent.class);
	}

	@Override
	public void addListener(final Listener listener) {
		if(listener instanceof ColliderListener) listeners.add((ColliderListener) listener);
	}

	@Override
	public void event(final Event event) {
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
