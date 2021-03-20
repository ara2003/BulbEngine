package com.greentree.engine.component;

import org.joml.Vector2f;

public final class Transform extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	private float lastx, lasty;
	@EditorData(def = "0")
	public float scaleX, scaleY;
	@EditorData(def = "0")
	public float x, y;
	
	public Transform() {
		lastx = x;
		lasty = y;
	}
	
	public void add(Vector2f vec) {
		x += vec.x;
		y += vec.y;
	}
	
	public float distanse(final Transform t) {
		final float x = this.x - t.x;
		final float y = this.y - t.y;
		return (float) Math.sqrt(x * x + y * y);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof Transform) {
			if(obj == this) return true;
			final Transform t = (Transform) obj;
			return x == t.x && y == t.y;
		}
		return false;
	}
	
	public double getDeltaX() {
		return x - lastx;
	}
	
	public double getDeltaY() {
		return y - lasty;
	}
	
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}
	
	public void setLocation(final double x, final double y) {
		setLocation((float) x, (float) y);
	}
	
	public void setLocation(final float x, final float y) {
		lastx = x;
		lasty = y;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void start() {
	}
	
	@Override
	public void update() {
		lastx = x;
		lasty = y;
	}
}
