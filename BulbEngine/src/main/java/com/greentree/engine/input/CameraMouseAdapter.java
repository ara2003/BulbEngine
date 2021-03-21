package com.greentree.engine.input;


/**
 * @author Arseny Latyshev
 *
 */
public abstract class CameraMouseAdapter implements CameraMouseListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void mouseDragged(int x1, int y1, int x2, int y2) {
	}
	
	@Override
	public void mouseMoved(int x1, int y1, int x2, int y2) {
	}
	
	@Override
	public void mousePress(int button, int x, int y) {
	}
	
	@Override
	public void mouseRelease(int button, int x, int y) {
	}
	
	@Override
	public void mouseRepeat(int button, int x, int y) {
	}
}
