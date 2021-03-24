package com.greentree.engine.component.ui;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerListenerManager;

public class ButtonListenerManager extends OneListenerListenerManager<ButtonListener> {
	
	private static final long serialVersionUID = 1L;
	
	public ButtonListenerManager() {
		super(ButtonListener.class);
	}
	
	@SuppressWarnings("exports")
	@Override
	public void event(final Event event) {
		if(event instanceof ButtonEvent) {
			final ButtonEvent buttonevent = (ButtonEvent) event;
			for(final ButtonListener l : listeners) l.click(buttonevent.getBuuton(), buttonevent.getMouseBuuton());
		}
	}
}
