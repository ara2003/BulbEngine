package com.greentree.graphics.input.listener;

import com.greentree.event.Listener;
import com.greentree.event.NecessarilyListenerManagers;
import com.greentree.graphics.input.listener.manager.KeyListenerManager;

@NecessarilyListenerManagers({KeyListenerManager.class})
public interface KeyListener extends Listener {

	void keyPress(int key);
	void keyRelease(int key);
	void keyRepeat(int key);

}
