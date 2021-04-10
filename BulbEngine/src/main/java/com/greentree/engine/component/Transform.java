package com.greentree.engine.component;

import java.util.function.Function;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.greentree.action.EventAction;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.UpdatingGameComponent;

public final class Transform extends UpdatingGameComponent {
	
	@EditorData
	public float rotateX, rotateY, rotateZ;
	@DefoultValue("1")
	@EditorData
	public float scaleX, scaleY, scaleZ;
	@EditorData
	public float x, y, z;
	
	private final EventAction<Transform> action;
	
	public Transform() {
		this.action = new EventAction<>();
	}
	
	private void action() {
		this.action.action(this);
	}
	
	public void addXY(final float x, final float y) {
		this.x += x;
		this.y += y;
		this.action();
	}
	
	public void addXY(final Vector2f vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.action();
	}
	
	public void addXYZ(final Vector3f mul) {
		this.x += mul.x;
		this.y += mul.y;
		this.z += mul.z;
		this.action();
	}
	
	public EventAction<Transform> getAction() {
		return this.action;
	}

	public void rotate(final float speedX, final float speedY, final float speedZ) {
		this.rotateX += speedX;
		this.rotateY += speedY;
		this.rotateZ += speedZ;
		this.action();
	}
	
	public void rotate(double f, final float speedX, final float speedY, final float speedZ) {
		this.rotateX += speedX*f;
		this.rotateY += speedY*f;
		this.rotateZ += speedZ*f;
		this.action();
	}
	
	public void set(final float x, final float y) {
		this.x = x;
		this.y = y;
		this.action();
	}
	
	@Override
	public void update() {
		this.action();
	}
	
	public float x() {
		return this.x;
	}
	
	public Vector2f xy() {
		return new Vector2f(this.x, this.y);
	}
	
	public Vector3fc xyz() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
	public float y() {
		return this.y;
	}
	
	public float z() {
		return this.z;
	}
	
	@FunctionalInterface
	public interface Tanslate<T> extends Function<T, T> {
	}

	public void addX(float x) {
		this.x = x;
		action();
	}

	public void addY(float y) {
		this.y = y;
		action();
	}


	public void addZ(float z) {
		this.z = z;
		action();
	}
	
}
