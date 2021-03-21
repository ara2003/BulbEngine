package com.greentree.bulbgl.input.listener;

import com.greentree.bulbgl.input.listener.manager.KeyListenerManager;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.NecessarilyListenerManagers;

@NecessarilyListenerManagers({KeyListenerManager.class})
public interface KeyListener extends Listener {

	void keyPress(int key);
	void keyRepeat(int key);
	void keyRelease(int key);
	
}
