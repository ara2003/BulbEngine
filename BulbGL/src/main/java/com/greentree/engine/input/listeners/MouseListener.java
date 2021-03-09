package com.greentree.engine.input.listeners;

import com.greentree.engine.event.Listener;
import com.greentree.engine.event.necessarilyListenerManagers;
import com.greentree.engine.input.MouseListenerManager;

@necessarilyListenerManagers({MouseListenerManager.class})
public interface MouseListener extends Listener {
	
	void mouseDragged(int x1, int y1, int x2, int y2);
	void mouseMoved(int x1, int y1, int x2, int y2);
	void mousePressed(int button, int x, int y);
	void mouseReleased(int button, int x, int y);
}
