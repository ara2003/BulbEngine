package com.greentree.engine.core.component;

import com.greentree.engine.core.object.GameComponent;
import com.greentree.event.Event;


public class NewComponentEvent implements Event {
	private final GameComponent component;
	private static final long serialVersionUID = 1L;

	public NewComponentEvent(GameComponent component) {
		this.component = component;
	}

	public GameComponent getComponent() {
		return component;
	}



}
