package com.greentree.engine.input.listeners;

import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;
import com.greentree.engine.input.KeyListenerManager;

@necessarilyListenerManagers({KeyListenerManager.class})
public interface KeyListener extends Listener {
	
	void keyPressed(int key);
	void keyReleased(int key);
	
}
