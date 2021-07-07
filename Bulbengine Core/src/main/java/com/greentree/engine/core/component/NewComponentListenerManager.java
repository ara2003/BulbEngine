package com.greentree.engine.core.component;

import com.greentree.event.ListenerManagerWithListener;

public class NewComponentListenerManager extends ListenerManagerWithListener<NewComponentEvent, NewComponentListener> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void event(NewComponentListener l, NewComponentEvent event) {
		l.newComponent(event.getComponent());
	}

	@Override
	protected boolean isUse(NewComponentListener listener) {
		return true;
	}



}
