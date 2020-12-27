package com.greentree.engine.component;

import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.math.vector.float2f;

public final class Transform extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	private float lastx, lasty;
	@EditorData(reserve = "0")
	public float scaleX, scaleY;
	@EditorData(reserve = "0")
	public float x, y;
	
	public Transform() {
		lastx = x;
		lasty = y;
	}
	
	public void addition(float2f vec) {
		x += vec.getX();
		y += vec.getY();
	}
	
	public float distanse(final Transform t) {
		final float x = this.x - t.x;
		final float y = this.y - t.y;
		return (float) Math.sqrt((x * x) + (y * y));
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof Transform) {
			if(obj == this) return true;
			final Transform t = (Transform) obj;
			return (x == t.x) && (y == t.y);
		}
		return false;
	}
	
	public double getDeltaX() {
		return x - lastx;
	}
	
	public double getDeltaY() {
		return y - lasty;
	}
	
	public void setLocation(double x, double y) {
		setLocation((float)x, (float)y);
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
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	
	@Override
	public void update() {
		lastx = x;
		lasty = y;
	}
	
}
