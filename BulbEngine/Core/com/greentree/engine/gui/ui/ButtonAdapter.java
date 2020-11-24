package com.greentree.engine.gui.ui;

import com.greentree.engine.event.Listener;

public abstract class ButtonAdapter implements Listener {

	private static final long serialVersionUID = 1L;
	private final Button button;
	
	public ButtonAdapter(final Button button) {
		this.button = button;
	}
	
	public final void click(final Button button, final int mouseButton) {
		if(button.equals(this.button)) click(mouseButton);
	}

	protected abstract void click(int mouseButton);
}
