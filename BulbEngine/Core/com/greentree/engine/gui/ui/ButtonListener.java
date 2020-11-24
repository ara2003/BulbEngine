package com.greentree.engine.gui.ui;

import com.greentree.engine.event.Listener;

public interface ButtonListener extends Listener {
	
	void click(Button button, int mouseButton);
}
