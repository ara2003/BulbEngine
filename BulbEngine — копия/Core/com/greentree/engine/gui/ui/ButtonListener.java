package com.greentree.engine.gui.ui;

import com.greentree.engine.event.Listener;
import com.greentree.engine.object.necessarily;

@necessarily(ButtonListenerManager.class)
public interface ButtonListener extends Listener {
	
	void click(Button button, int mouseButton);
}
