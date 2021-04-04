package com.greentree.bulbgl;

import com.greentree.event.EventSystem;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class WindowI implements AutoCloseable {
	
	public abstract void close();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract boolean isActive();
	public abstract boolean isShouldClose();
	public abstract void startRender();
	public abstract void finishRender();
	public abstract void setEventSystem(EventSystem eventSystem);
	
}
