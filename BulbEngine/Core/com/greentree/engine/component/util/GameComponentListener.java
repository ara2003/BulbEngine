package com.greentree.engine.component.util;

import com.greentree.engine.event.Listener;
import com.greentree.engine.object.GameComponent;

public interface GameComponentListener extends Listener {

	default void create(final GameComponent component) {
	}

	default void destroy(final GameComponent component) {
	}
}
