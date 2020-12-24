package com.greentree.engine.gui.ui;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.event.Event;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.ListenerManager;

public class ButtonListenerManager extends ListenerManager {
	
	private static final long serialVersionUID = 1L;
	private final List<ButtonAdapter> listeners = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public ButtonListenerManager() {
		super(ButtonEvent.class);
	}
	
	@Override
	public void addListener(final Listener listener) {
		if(listener instanceof ButtonAdapter) listeners.add((ButtonAdapter) listener);
	}
	
	@Override
	public void event(final Event event) {
		if(event instanceof ButtonEvent) {
			final ButtonEvent buttonevent = (ButtonEvent) event;
			for(final ButtonAdapter l : listeners) l.click(buttonevent.getBuuton(), buttonevent.getMouseBuuton());
		}
	}
}
