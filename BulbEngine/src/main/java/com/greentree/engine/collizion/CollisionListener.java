package com.greentree.engine.collizion;

import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;

/** @author Arseny Latyshev */
@necessarilyListenerManagers({CollisionListenerManager.class})
public interface CollisionListener extends Listener {
}
