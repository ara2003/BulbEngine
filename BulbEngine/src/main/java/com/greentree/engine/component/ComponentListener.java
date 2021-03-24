package com.greentree.engine.component;

import com.greentree.engine.event.Listener;
import com.greentree.engine.event.NecessarilyListenerManagers;
import com.greentree.engine.object.GameComponent;


/**
 * @author Arseny Latyshev
 *
 */
@NecessarilyListenerManagers({ComponentListenerManager.class})
public interface ComponentListener extends Listener {

	public void create(GameComponent component);
	public void destroy(GameComponent component);
	
}
