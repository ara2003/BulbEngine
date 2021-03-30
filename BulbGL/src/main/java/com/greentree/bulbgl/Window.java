package com.greentree.bulbgl;

import com.greentree.event.EventSystem;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class Window implements AutoCloseable {
	
	public abstract void close();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract boolean isActive();
	public abstract boolean isShouldClose();
	public abstract void startRender();
	public abstract void finishRender();
	public abstract void setEventSystem(@SuppressWarnings("exports") EventSystem eventSystem);
	public abstract int getIndexOf(String name);
	public abstract String getKeyName(int key);
	
}
