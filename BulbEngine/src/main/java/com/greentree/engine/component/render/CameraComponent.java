package com.greentree.engine.component.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

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
public class CameraComponent extends GameComponent {
	
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
		return new Matrix4f().frustum(-w, w, -h, h, 1F, 100.0F).lookAt(this.position.xyz(), this.cameraDirection.add(this.position.xyz(), new Vector3f()), this.up);
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
	
	/**
	 * @param pitch - в радианах
	 * @param yaw - в радианах
	 */
	public void setAngle(double pitch, double yaw){
		Vector3f res = new Vector3f(0);
		res.x = (float) (Math.cos(pitch) * Math.cos(yaw));
		res.y = (float) Math.sin(pitch);
		res.z = (float) (Math.cos(pitch) * Math.sin(yaw));
		res.normalize();
		setFront(res);
	}
	
	public void setFront(final Vector3f res) {
		synchronized(cameraDirection) {
			this.cameraDirection.set(res);
			this.up.cross(this.cameraDirection, this.cameraRight);
		}
	}
	
	@Override
	protected void start() {
		this.up.cross(this.cameraDirection, this.cameraRight);
		this.position = this.getComponent(Transform.class);
	}
	
	

	public void translate() {
		CameraComponent.GL.glScalef(Windows.getWindow().getWidth() / this.width, Windows.getWindow().getHeight() / this.height, 1);
		CameraComponent.GL.glTranslatef(this.width / 2 - this.getX(), this.height / 2 - this.getY(), 0);
	}
	public void untranslate() {
		CameraComponent.GL.glTranslatef(-this.width / 2 + this.getX(), -this.height / 2 + this.getY(), 0);
		CameraComponent.GL.glScalef(this.width / Windows.getWindow().getWidth(),  this.height / Windows.getWindow().getHeight(), 1);
	}
	
	public void unTranslateNotMove() {
//		CameraComponent.GL.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		CameraComponent.GL.glLoadIdentity();
		
		CameraComponent.GL.glTranslatef(-this.width / 2, -this.height / 2, 0);
		CameraComponent.GL.glScalef(this.width / Windows.getWindow().getWidth(),  this.height / Windows.getWindow().getHeight(), 1);
		
//		GL11.glOrtho(0.0, Windows.getWindow().getWidth(), Windows.getWindow().getHeight(), 0.0, 0.0, 1.0);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}
	
	public void translateNotMove() {
//		CameraComponent.GL.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		CameraComponent.GL.glLoadIdentity();
		
//		GL11.glOrtho(0.0, Windows.getWindow().getWidth(), Windows.getWindow().getHeight(), 0.0, 0.0, 1.0);
		CameraComponent.GL.glScalef(Windows.getWindow().getWidth() / this.width, Windows.getWindow().getHeight() / this.height, 1);
		CameraComponent.GL.glTranslatef(this.width / 2, this.height / 2, 0);
		
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}
	
	public float WindowToCameraX(final float x) {
		return x * width / Windows.getWindow().getWidth() - this.width / 2;
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
	
}
