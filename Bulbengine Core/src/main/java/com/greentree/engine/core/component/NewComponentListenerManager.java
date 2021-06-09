package com.greentree.engine.core.component;

import com.greentree.event.ListenerManagerWithListener;

public class NewComponentListenerManager extends ListenerManagerWithListener<NewComponentEvent, NewComponent> {

	@Override
	protected void event(NewComponent l, NewComponentEvent event) {
		l.newComponent(event.getComponent());
	}

	@Override
	protected boolean isUse(NewComponent listener) {
		return true;
	}



}
