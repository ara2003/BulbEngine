package com.greentree.graphics.input.listener;

import com.greentree.event.Listener;
import com.greentree.event.NecessarilyListenerManagers;
import com.greentree.graphics.input.listener.manager.MouseListenerManager;

@NecessarilyListenerManagers({MouseListenerManager.class})
public interface MouseListener extends Listener {
	
	void mouseDragged(int x1, int y1, int x2, int y2);
	void mouseMoved(int x1, int y1, int x2, int y2);
	void mousePress(int button, int x, int y);
	void mouseRelease(int button, int x, int y);
	void mouseRepeat(int button, int x, int y);
}
