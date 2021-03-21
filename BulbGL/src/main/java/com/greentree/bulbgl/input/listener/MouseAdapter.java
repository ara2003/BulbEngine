package com.greentree.bulbgl.input.listener;

public abstract class MouseAdapter implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void mouseDragged(final int x1, final int y1, final int x2, final int y2) {
	}
	
	@Override
	public void mouseMoved(final int x1, final int y1, final int x2, final int y2) {
	}
	
	@Override
	public void mousePress(final int button, final int x, final int y) {
	}
	
	@Override
	public void mouseRelease(final int button, final int x, final int y) {
	}

	@Override
	public void mouseRepeat(int button, int x, int y) {
	}
}
