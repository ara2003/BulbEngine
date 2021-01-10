package com.greentree.engine.component.util;

import com.greentree.engine.GameComponent;
import com.greentree.engine.necessarily;
import com.greentree.engine.event.Listener;

@necessarily(GameComponent.class)
public interface GameComponentListener extends Listener {
	
	default void create(final GameComponent component) {
	}
	
	default void destroy(final GameComponent component) {
	}
}
