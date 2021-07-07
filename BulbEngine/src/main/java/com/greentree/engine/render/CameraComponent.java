package com.greentree.engine.render;

import org.joml.Matrix4f;
import org.joml.Vector2fc;

import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.common.math.vector.AbstractVector3f;
import com.greentree.common.math.vector.Vector2f;
import com.greentree.common.math.vector.Vector3f;
import com.greentree.engine.Windows;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.StartGameComponent;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
public class CameraComponent extends StartGameComponent {

	@Required
	@EditorData
	private float width, height;

	public Transform position;
	private final AbstractVector3f cameraDirection = new Vector3f(), cameraRight = new Vector3f();

	private double pitch;

	private double yaw;
	{
		getCameraUp().cross(getCameraDirection(), getCameraRight());
	}
	
	public AbstractVector3f getCameraDirection() {
		setFront((float) (Math.cos(pitch) * Math.cos(yaw)),(float) Math.sin(pitch),(float) (Math.cos(pitch) * Math.sin(yaw)));
		return cameraDirection;
	}

	public AbstractVector3f getCameraRight() {
		return cameraRight;
	}

	public Vector3f getCameraUp() {
		return new Vector3f(0, 1, 0);
	}

	public float getHeight() {
		return height;
	}

	public Matrix4f getProjection() {
		final float w = 1.0F;//TODO
		final float h = getHeight() / getWidth() * w;
		return new Matrix4f().frustum(-w, w, -h, h, 1.0F, 10000.0F).lookAt(position.position.toJoml(), cameraDirection.add(position.position, new Vector3f()).toJoml(), getCameraUp().toJoml());
	}

	public AbstractVector2f getUVPosition(final Vector2fc position) {
		return WindowToCamera(position).add(-position.x(), -position.y()).mul(2).div(width, height);
	}

	public float getWidth() {
		return width;
	}

	public AbstractVector2f getWorldPosition(final AbstractVector2f position) {
		final Vector2f vec = new Vector2f(position);
		vec.x += this.position.position.x();
		vec.x /= 1f * Windows.getWindow().getWidth() / width;
		vec.y += this.position.position.y();
		vec.y /= 1f * Windows.getWindow().getHeight() / height;
		return vec;
	}

	public float getX() {
		return position.position.x();
	}
	public float getY() {
		return position.position.y();
	}

	public float getZ() {
		return position.position.z();
	}

	/**
	 * @param pitch - в радианах
	 * @param yaw - в радианах
	 */
	public void setAngle(double pitch, double yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
	}

	private void setFront(float x, float y, float z) {
		synchronized(cameraDirection) {
			cameraDirection.set(x, y, z);
			cameraDirection.normalize();
			cameraDirection.cross(getCameraUp(), cameraRight);
		}
	}

	@Override
	public void start() {
		position = this.getComponent(Transform.class);
	}

	public void translateAs3DCamera() {
		Graphics.pushMatrix();
		final float w = 1.0F;//TODO
		final float h = getHeight() / getWidth() * w;
		Graphics.frustum(-w, w, -h, h, -1.0F, -10000.0F);
		Graphics.translate(position.position.x(), position.position.y(), position.position.z());
		Graphics.rotate( -yaw*180 / Math.PI, -pitch*180 / Math.PI, 0);
	}
	
	public void translateAsCamera() {
		Graphics.pushMatrix();
		Graphics.scale(2 / width, 2 / height);
	}

	public void translateAsWindow() {
		Graphics.pushMatrix();
		Graphics.translate(-1, 1);
		Graphics.scale(2.0f / Windows.getWindow().getWidth(), 2.0f / Windows.getWindow().getHeight());
	}

	public void translateAsWorld() {
		Graphics.pushMatrix();
		Graphics.scale(2 / width, 2 / height);
		Graphics.translate(-getX(), -getY(), -getZ());
	}

	public void untranslate() {
		Graphics.popMatrix();
	}

	public AbstractVector2f WindowToCamera(final Vector2fc position) {
		return new Vector2f(WindowToCameraX(position.x()), WindowToCameraY(position.y()));
	}

	public float WindowToCameraX(final float x) {
		return x * width / Windows.getWindow().getWidth() + getX();
	}

	public float WindowToCameraY(final float y) {
		return y * height / Windows.getWindow().getHeight() + getY();
	}
	public Vector2f WorldToCamera(final Vector2fc position) {
		return new Vector2f(WorldToCameraX(position.x()), WorldToCameraY(position.y()));
	}

	public float WorldToCameraX(final float x) {
		return x - position.position.x();
	}

	public float WorldToCameraY(final float y) {
		return y - position.position.y();
	}

}
