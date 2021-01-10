package com.greentree.engine.input;

import com.greentree.engine.necessarily;
import com.greentree.engine.event.Listener;

@necessarily({KeyListenerManager.class})
public interface KeyListener extends Listener {
	
	void keyPressed(int key);
	void keyReleased(int key);
}
