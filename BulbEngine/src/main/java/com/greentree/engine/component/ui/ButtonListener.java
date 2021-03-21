package com.greentree.engine.component.ui;

import com.greentree.engine.event.Listener;
import com.greentree.engine.event.NecessarilyListenerManagers;

@NecessarilyListenerManagers(ButtonListenerManager.class)
public interface ButtonListener extends Listener {
	
	void click(Button button, int mouseButton);
}
