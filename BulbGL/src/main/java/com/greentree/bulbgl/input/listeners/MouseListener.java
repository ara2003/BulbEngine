package com.greentree.bulbgl.input.listeners;

import com.greentree.bulbgl.input.util.MouseListenerManager;
import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;

@necessarilyListenerManagers({MouseListenerManager.class})
public interface MouseListener extends Listener {
	
	void mouseDragged(int x1, int y1, int x2, int y2);
	void mouseMoved(int x1, int y1, int x2, int y2);
	void mousePress(int button, int x, int y);
	void mouseRelease(int button, int x, int y);
	void mouseRepeat(int button, int x, int y);
}
