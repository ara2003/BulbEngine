package com.greentree.engine.component.util;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.ListenerManager;

public class GameComponentListenerManager extends ListenerManager {
	
	private static final long serialVersionUID = 1L;
	private final List<GameComponentListener> listeners = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public GameComponentListenerManager() {
		super(GameComponentEvent.class);
	}

	@Override
	public void addListener(final Listener listener) {
		if(listener instanceof GameComponentListener) listeners.add((GameComponentListener) listener);
	}

	@Override
	public void event(final Event event) {
		if(event instanceof GameComponentEvent) {
			final GameComponentEvent objevent = (GameComponentEvent) event;
			switch(objevent.getEventType()) {
				case create:
					for(final GameComponentListener lis : listeners) lis.create(objevent.getComponent());
				break;
				case destroy:
					for(final GameComponentListener lis : listeners) lis.destroy(objevent.getComponent());
				break;
			}
		}
	}
}
