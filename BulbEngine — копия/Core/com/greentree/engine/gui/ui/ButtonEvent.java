package com.greentree.engine.gui.ui;

import com.greentree.engine.event.Event;

public class ButtonEvent implements Event {
	
	private static final long serialVersionUID = 1L;
	private final int mouseButton;
	private final Button UIButton;
	
	public ButtonEvent(final Button UIButton, final int mouseButton) {
		this.UIButton = UIButton;
		this.mouseButton = mouseButton;
	}
	
	public Button getBuuton() {
		return UIButton;
	}
	
	public int getMouseBuuton() {
		return mouseButton;
	}
}
