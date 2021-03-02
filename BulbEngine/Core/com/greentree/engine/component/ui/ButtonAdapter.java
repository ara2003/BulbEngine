package com.greentree.engine.component.ui;

public abstract class ButtonAdapter implements ButtonListener {
	
	private static final long serialVersionUID = 1L;
	private final Button button;
	
	public ButtonAdapter(final Button button) {
		this.button = button;
	}
	
	@Override
	public final void click(final Button button, final int mouseButton) {
		if(button.equals(this.button)) click(mouseButton);
	}
	
	protected abstract void click(int mouseButton);
}
