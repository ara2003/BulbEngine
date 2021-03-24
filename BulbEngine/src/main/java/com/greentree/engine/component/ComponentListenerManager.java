package com.greentree.engine.component;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerListenerManager;


/**
 * @author Arseny Latyshev
 *
 */
public class ComponentListenerManager extends OneListenerListenerManager<ComponentListener> {
	private static final long serialVersionUID = 1L;

	public ComponentListenerManager() {
		super(ComponentListener.class);
	}

	@SuppressWarnings("exports")
	@Override
	public void event(Event event) {
		if(event instanceof ComponentEvent) {
			ComponentEvent componentEvent = (ComponentEvent)event;
			switch(componentEvent.getEventType()) {
				case create -> {
					for(ComponentListener l : listeners)l.create(componentEvent.getComponent());
				}
				case destroy -> {
					for(ComponentListener l : listeners)l.destroy(componentEvent.getComponent());
				}
			}
		}
	}
	
}
