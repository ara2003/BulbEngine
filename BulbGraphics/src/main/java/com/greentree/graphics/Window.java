package com.greentree.graphics;

/** @author Arseny Latyshev */
public class Window extends GLFWContext {

	public Window(final String title, final int width, final int height, final boolean resizable, final boolean fullscreen, final GLFWContext share) {
		super(title, width, height, resizable, fullscreen, true, share);
	}
	

}
