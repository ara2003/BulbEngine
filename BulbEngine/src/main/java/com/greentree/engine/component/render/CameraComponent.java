package com.greentree.engine.component.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.engine.Windows;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.system.NecessarilySystems;
import com.greentree.engine.system.CameraRenderSystem;

/** @author Arseny Latyshev */
@NecessarilySystems({CameraRenderSystem.class})
@RequireComponent({Transform.class})
public class Camera extends GameComponent {
	
	private final static GraphicsI GL = BulbGL.getGraphics();
	@DefoultValue("window::width")
	@EditorData()
	private float width;
	@DefoultValue("window::height")
	@EditorData()
	private float height;
	private Transform position;
	private final Vector3f cameraDirection = new Vector3f(0, 1, -1), cameraRight = new Vector3f();
	private final Vector3f up = new Vector3f(0, 1, 0);
	
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
//		return new Matrix4f().frustum(-w, w, -h, h, 1.0F, 100.0F).translate(this.position.xyz());
		return new Matrix4f().frustum(-w, w, -h, h, 1F, 100.0F).lookAt(position.xyz(), cameraDirection.add(position.xyz(), new Vector3f()), up);
	}
	
	public Vector2f getUVPosition(final Vector2fc position) {
		return this.WorldToCamera(position).mul(2).div(this.width, this.height);
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
	
	public float getY() {
		return this.position.y();
	}
	
	@Override
	protected void start() {
		this.up.cross(this.cameraDirection, this.cameraRight);
		this.position = this.getComponent(Transform.class);
	}
	
	public void translate() {
		Camera.GL.glScalef(1f * Windows.getWindow().getWidth() / this.width, 1f * Windows.getWindow().getHeight() / this.height, 1);
		Camera.GL.glTranslatef(this.width / 2 - this.getX(), this.height / 2 - this.getY(), 0);
	}
	
	public void untranslate() {
		Camera.GL.glTranslatef(-this.width / 2 + this.getX(), -this.height / 2 + this.getY(), 0);
		Camera.GL.glScalef(1f * this.width / Windows.getWindow().getWidth(), 1f * this.height / Windows.getWindow().getHeight(), 1);
	}
	
	public float WindowToCameraX(final int x) {
		return x - this.width / 2;
	}
	
	public float WindowToCameraY(final int y) {
		return this.height / 2 - y;
	}
	
	public Vector2f WorldToCamera(final Vector2fc position) {
		final Vector2f vec = new Vector2f(position);
		vec.x *= 1f * Windows.getWindow().getWidth() / this.width;
		vec.x -= this.position.x();
		vec.y *= 1f * Windows.getWindow().getHeight() / this.height;
		vec.y -= this.position.y();
		return vec;
	}

	public void setFront(Vector3f res) {
		cameraDirection.set(res);
		this.up.cross(this.cameraDirection, this.cameraRight);
	}
	
}
