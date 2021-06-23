package com.greentree.engine.core.component;

import com.greentree.event.ListenerManagerWithListener;

public class NewComponentListenerManager extends ListenerManagerWithListener<NewComponentEvent, NewComponentListener> {

	@Override
	protected void event(NewComponentListener l, NewComponentEvent event) {
		l.newComponent(event.getComponent());
	}

	@Override
	protected boolean isUse(NewComponentListener listener) {
		return true;
	}



}
