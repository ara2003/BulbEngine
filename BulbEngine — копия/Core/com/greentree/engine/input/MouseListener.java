package com.greentree.engine.input;

import com.greentree.engine.event.Listener;

public interface MouseListener extends Listener {
	//void mouseWheelMoved(int i);
	
	void mouseDragged(int x1, int y1, int x2, int y2);
	void mouseMoved(int x1, int y1, int x2, int y2);
	void mousePressed(int button, int x, int y);
	void mouseReleased(int button, int x, int y);
}
