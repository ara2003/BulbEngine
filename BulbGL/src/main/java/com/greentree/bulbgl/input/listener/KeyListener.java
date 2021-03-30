package com.greentree.bulbgl.input.listener;

import com.greentree.bulbgl.input.listener.manager.KeyListenerManager;
import com.greentree.event.Listener;
import com.greentree.event.NecessarilyListenerManagers;

@NecessarilyListenerManagers({KeyListenerManager.class})
public interface KeyListener extends Listener {

	void keyPress(int key);
	void keyRepeat(int key);
	void keyRelease(int key);
	
}
