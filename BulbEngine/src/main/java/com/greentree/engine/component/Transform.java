package com.greentree.engine.component;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.greentree.action.EventAction;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.GameComponent;

public final class Transform extends GameComponent {

	@EditorData
	private float rotateX, rotateY, rotateZ;
	@EditorData
	private float scaleX = 1, scaleY = 1, scaleZ = 1;
	@EditorData
	private float x, y, z;
	@EditorData(value = "static")
	private boolean isStatic = false;
	private final EventAction<Transform> action;
	private boolean update;

	public Transform() {
		action = new EventAction<>();
	}

	private void action() {
		if(isStatic) throw new UnsupportedOperationException("Transform is static " + getObject());
		update = true;
	}

	public void addX(final float x) {
		this.x += x;
		action();
	}

	public void addXY(final float x, final float y) {
		this.x += x;
		this.y += y;
		action();
	}

	public void addXY(final Vector2f vec) {
		x += vec.x;
		y += vec.y;
		action();
	}

	public void addXYZ(final Vector3f mul) {
		x += mul.x;
		y += mul.y;
		z += mul.z;
		action();
	}

	public void addY(final float y) {
		this.y += y;
		action();
	}

	public void addZ(final float z) {
		this.z += z;
		action();
	}

	public EventAction<Transform> getAction() {
		return action;
	}

	public float getRotateX() {
		update0();
		return rotateX;
	}

	public float getRotateY() {
		update0();
		return rotateY;
	}

	public float getRotateZ() {
		update0();
		return rotateZ;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void rotate(final double f, final float speedX, final float speedY, final float speedZ) {
		rotateX += speedX * f;
		rotateY += speedY * f;
		rotateZ += speedZ * f;
		action();
	}

	public void rotate(final float speedX, final float speedY, final float speedZ) {
		rotateX += speedX;
		rotateY += speedY;
		rotateZ += speedZ;
		action();
	}

	public void set(final float x, final float y) {
		this.x = x;
		this.y = y;
		action();
	}

	public void setRotateX(final float rotateX) {
		this.rotateX = rotateX;
		action();
	}

	public void setRotateY(final float rotateY) {
		this.rotateY = rotateY;
		action();
	}

	public void setRotateZ(final float rotateZ) {
		this.rotateZ = rotateZ;
		action();
	}

	public void subXY(Vector2f vec) {
		x -= vec.x;
		y -= vec.y;
		action();
	}

	public void update() {
		action.action(this);
	}
	private void update0() {
		if(!update) return;
		update = false;
		update();
	}

	public float x() {
		update0();
		return x;
	}

	public Vector2f xy() {
		update0();
		return new Vector2f(x, y);
	}

	public Vector3f xyz() {
		update0();
		return new Vector3f(x, y, z);
	}

	public float y() {
		update0();
		return y;
	}

	public float z() {
		update0();
		return z;
	}

	public Vector3fc rotateXYZ() {
		return new Vector3f(rotateX, rotateY, rotateZ);
	}
	public Vector3fc scaleXYZ() {
		return new Vector3f(getScaleX(), getScaleY(), getScaleZ());
	}

	public float getScaleX() {
		return scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public float getScaleZ() {
		return scaleZ;
	}

	public Matrix4f getModexViewMatrix() {
		final Matrix4f modelView = new Matrix4f().identity();
		modelView.translate(x(), y(), z());
		modelView.scale(getScaleX(), getScaleY(), getScaleZ());
		modelView.rotateXYZ(getRotateX(), getRotateY(), getRotateZ());
		return modelView;
	}
}
