package com.greentree.engine.component;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.greentree.action.EventAction;
import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.component.EditorData;

public final class Transform extends GameComponent {
	
	@EditorData
	private float scaleX, scaleY, scaleZ;
	@EditorData
	private float x, y, z;
	private final EventAction<Transform> action;
	
	public Transform() {
		this.action = new EventAction<>();
	}
	
	private void action() {
		this.action.action(this);
	}
	
	public void addXY(final Vector2f vec) {
		this.set(vec.x + this.x, vec.y + this.y);
	}

	public void addX(final float x) {
		this.x += x;
		this.action();
	}
	public void addXY(final float x, float y) {
		this.x += x;
		this.y += y;
		this.action();
	}
	
	public void addY(final float y) {
		this.y += y;
		this.action();
	}
	
	public void addZ(final float z) {
		this.z += z;
		this.action();
	}
	
	public float distanse(final Transform t) {
		final float dx = this.x - t.x;
		final float dy = this.y - t.y;
		final float dz = this.z - t.z;
		return (float) Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
	}
	
	public float distanseXY(final Transform t) {
		final float x = this.x - t.x;
		final float y = this.y - t.y;
		return (float) Math.sqrt(x * x + y * y);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof Transform) {
			if(obj == this) return true;
			final Transform t = (Transform) obj;
			return this.x == t.x && this.y == t.y && this.z == t.z;
		}
		return false;
	}
	
	public EventAction<Transform> getAction() {
		return this.action;
	}
	
	public Vector3f xyz() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
	public float x() {
		return this.x;
	}
	
	public float y() {
		return this.y;
	}
	
	public float z() {
		return this.z;
	}
	
	public void set(final double x, final double y) {
		this.set((float) x, (float) y);
	}
	
	public void set(final double x, final double y, final double z) {
		this.setLocation((float) x, (float) y, (float) z);
	}
	
	public void set(final float x, final float y) {
		this.setLocation(x, y, this.z);
	}
	
	public void setX(float x){
		
		action();
	}
	
	private void setLocation(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.action();
	}
	
	public Vector2f xy() {
		return new Vector2f(this.x, this.y);
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.x = x;
		action();
	}

	public void addY(double y) {
		addY((float)y);
	}

}
