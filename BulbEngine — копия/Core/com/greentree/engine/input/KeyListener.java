package com.greentree.engine.input;

import com.greentree.engine.event.Listener;
import com.greentree.engine.object.necessarily;

@necessarily({KeyListenerManager.class})
public interface KeyListener extends Listener {
	
	void keyPressed(int key);
	void keyReleased(int key);
}
