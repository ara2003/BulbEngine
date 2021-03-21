package com.greentree.engine.component;

import org.joml.Vector2f;
import org.joml.Vector3f;

public final class Transform extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	private float lastx, lasty, lastz;
	@EditorData(def = "0")
	public float scaleX, scaleY, scaleZ;
	@EditorData(def = "0")
	public float x, y, z;
	
	public Transform() {
		lastx = x;
		lasty = y;
	}
	
	public void add(Vector2f vec) {
		x += vec.x;
		y += vec.y;
	}

	public float distanseXY(final Transform t) {
		final float x = this.x - t.x;
		final float y = this.y - t.y;
		return (float) Math.sqrt(x * x + y * y);
	}
	public float distanse(final Transform t) {
		final float dx = this.x - t.x;
		final float dy = this.y - t.y;
		final float dz = this.z - t.z;
		return (float) Math.sqrt(dx * dx + dy * dy + dz*dz);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof Transform) {
			if(obj == this) return true;
			final Transform t = (Transform) obj;
			return x == t.x && y == t.y && z == t.z;
		}
		return false;
	}
	
	public double getDeltaX() {
		return x - lastx;
	}
	
	public double getDeltaY() {
		return y - lasty;
	}
	
	public double getDeltaZ() {
		return z - lastz;
	}
	
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}
	
	public Vector3f getVector() {
		return new Vector3f(x, y, z);
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
		lastz = z;
	}
}
