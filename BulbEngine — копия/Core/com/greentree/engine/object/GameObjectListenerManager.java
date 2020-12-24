package com.greentree.engine.object;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.ListenerManager;

public class GameObjectListenerManager extends ListenerManager {
	
	private static final long serialVersionUID = 1L;
	private final List<GameObjectListener> listeners = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	GameObjectListenerManager() {
		super(GameObjectEvent.class);
	}
	
	@Override
	public void addListener(final Listener listener) {
		if(listener instanceof GameObjectListener) listeners.add((GameObjectListener) listener);
	}
	
	@Override
	public void event(final Event event) {
		if(event instanceof GameObjectEvent) {
			final GameObjectEvent objevent = (GameObjectEvent) event;
			switch(objevent.getEventType()) {
				case destroy:
					for(final GameObjectListener lis : listeners) lis.destroy(objevent.getObject());
					break;
				case create:
					for(final GameObjectListener lis : listeners) lis.create(objevent.getObject());
					break;
			}
		}
	}
}
