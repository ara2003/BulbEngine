package com.greentree.engine.input;

import com.greentree.engine.event.Listener;

public interface KeyListener extends Listener {
	
	void keyPressed(int code);
	void keyReleased(int code);
}
