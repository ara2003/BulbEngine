package com.greentree.bulbgl.input.listeners;

import com.greentree.bulbgl.input.util.KeyListenerManager;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;

@necessarilyListenerManagers({KeyListenerManager.class})
public interface KeyListener extends Listener {

	void keyPress(int key);
	void keyRepeat(int key);
	void keyRelease(int key);
	
}
