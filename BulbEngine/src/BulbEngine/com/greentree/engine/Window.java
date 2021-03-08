package com.greentree.engine;

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
	public abstract boolean isKeyDown(int key);
	public abstract void startRender();
	public abstract void finishRender();
	public abstract int getIndexOf(String key);
	
}
