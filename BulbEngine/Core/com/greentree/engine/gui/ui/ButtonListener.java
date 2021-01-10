package com.greentree.engine.gui.ui;

import com.greentree.engine.necessarily;
import com.greentree.engine.event.Listener;

@necessarily(ButtonListenerManager.class)
public interface ButtonListener extends Listener {
	
	void click(Button button, int mouseButton);
}
