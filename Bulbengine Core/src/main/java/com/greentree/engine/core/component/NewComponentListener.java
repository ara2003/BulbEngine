package com.greentree.engine.core.component;

import com.greentree.engine.core.object.GameComponent;
import com.greentree.event.Listener;
import com.greentree.event.NecessarilyListenerManagers;

@FunctionalInterface
@NecessarilyListenerManagers({NewComponentListenerManager.class})
public interface NewComponentListener extends Listener {

	void newComponent(GameComponent c);

}
