package com.greentree.engine.component.ui;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.OneListenerMutiEventListenerManager;

public class ButtonListenerManager extends OneListenerMutiEventListenerManager<ButtonListener> {
	
	private static final long serialVersionUID = 1L;
	
	public ButtonListenerManager() {
		super(ButtonListener.class, ButtonEvent.class);
	}
	
	@SuppressWarnings("exports")
	@Override
	public void event0(final Event event) {
		if(event instanceof ButtonEvent) {
			final ButtonEvent buttonevent = (ButtonEvent) event;
			for(final ButtonListener l : listeners) l.click(buttonevent.getBuuton(), buttonevent.getMouseBuuton());
		}
	}
}
