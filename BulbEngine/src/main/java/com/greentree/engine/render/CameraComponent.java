package com.greentree.engine.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

import com.greentree.engine.Windows;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
public class CameraComponent extends GameComponent {
	
	@EditorData(required = true)
	private float width, height;
	
	private Transform position;
	private final Vector3f cameraDirection = new Vector3f(), cameraRight = new Vector3f();
	private final Vector3f up = new Vector3f(0, 1, 0);
	{
		setFront(0, 0, 1);
	}
	public Vector3f getCameraDirection() {
		return this.cameraDirection;
	}
	
	public Vector3f getCameraRight() {
		return this.cameraRight;
	}
	
	public Vector3f getCameraUp() {
		return this.up;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public Matrix4f getProjection() {
		final float w = 1.0F;
		final float h = this.getHeight() / this.getWidth() * w;
		return new Matrix4f().frustum(-w, w, -h, h, 0.9F, 10000.0F).lookAt(this.position.xyz(), this.cameraDirection.add(this.position.xyz(), new Vector3f()), this.up);
	}
	
	public Vector2f getUVPosition(final Vector2fc position) {
		return this.WindowToCamera(position).mul(2).div(this.width, this.height);
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public Vector2f getWorldPosition(final Vector2fc position) {
		final Vector2f vec = new Vector2f(position);
		vec.x += this.position.x();
		vec.x /= 1f * Windows.getWindow().getWidth() / this.width;
		vec.y += this.position.y();
		vec.y /= 1f * Windows.getWindow().getHeight() / this.height;
		return vec;
	}

	public float getX() {
		return this.position.x();
	}
	public float getZ() {
		return this.position.z();
	}
	
	public float getY() {
		return this.position.y();
	}
	
	/**
	 * @param pitch - в радианах
	 * @param yaw - в радианах
	 */
	public void setAngle(double pitch, double yaw){
		setFront((float) (Math.cos(pitch) * Math.cos(yaw)),(float) Math.sin(pitch),(float) (Math.cos(pitch) * Math.sin(yaw)));
	}
	
	public void setFront(float x, float y, float z) {
		synchronized(cameraDirection) {
			this.cameraDirection.set(x, y, z);
			cameraDirection.normalize();
			this.cameraDirection.cross(this.up, this.cameraRight);
			cameraRight.normalize();
		}
		
	}
	
	@Override
	protected void start() {
		this.up.cross(this.cameraDirection, this.cameraRight);
		this.position = this.getComponent(Transform.class);
	}
	public void untranslate() {
		Graphics.popMatrix();
	}
	
	public float WindowToCameraX(final float x) {
		return (x) * width / Windows.getWindow().getWidth();
	}
	
	public float WindowToCameraY(final float y) {
		return (y) * height / Windows.getWindow().getHeight();
	}
	
	public Vector2f WindowToCamera(final Vector2fc position) {
		return new Vector2f(WindowToCameraX(position.x()), WindowToCameraY(position.y()));
	}
	
	public float WorldToCameraX(final float x) {
		return (x) - position.x();
	}
	
	public float WorldToCameraY(final float y) {
		return (y) - position.y();
	}
	
	public Vector2f WorldToCamera(final Vector2fc position) {
		return new Vector2f(WorldToCameraX(position.x()), WorldToCameraY(position.y()));
	}

	public void translateAsWorld() {
		Graphics.pushMatrix();
		Graphics.scale(2 / this.width, 2 / this.height);
		Graphics.translate(-this.getX(), -this.getY(), -this.getZ());
	}
	public void translateAsCamera() {
		Graphics.pushMatrix();
		Graphics.scale(2 / this.width, 2 / this.height);
	}

	public void translateAsWindow() {
		Graphics.pushMatrix();
		Graphics.translate(-1, 1);
		Graphics.scale(2.0f / Windows.getWindow().getWidth(), 2.0f / Windows.getWindow().getHeight());
	}
	
}
