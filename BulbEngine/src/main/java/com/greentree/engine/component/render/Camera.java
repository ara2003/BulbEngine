package com.greentree.engine.component.render;

import org.joml.Vector2f;
import org.joml.Vector2fc;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.engine.Windows;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.system.NecessarilySystems;
import com.greentree.engine.system.RenderSystem;

/** @author Arseny Latyshev */
@NecessarilySystems({RenderSystem.class})
@RequireComponent({Transform.class})
public class Camera extends GameComponent {
	
	private final static GraphicsI GL = BulbGL.getGraphics();
	@DefoultValue("window::width")
	@EditorData()
	private int width;
	@DefoultValue("window::height")
	@EditorData()
	private int height;
	private Transform position;
	
	public int getHeight() {
		return this.height;
	}
	
	public Vector2f getUVPosition(final Vector2fc position) {
		return this.WorldToCamera(position).mul(2).div(this.width, this.height);
	}
	
	public int getWidth() {
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
		this.position = this.getComponent(Transform.class);
	}
	
	public void translate() {
		GL.glScalef(1f * Windows.getWindow().getWidth() / this.width, 1f * Windows.getWindow().getHeight() / this.height, 1);
		GL.glTranslatef(this.width / 2 - this.getX(), this.height / 2 - this.getY(), 0);
	}
	
	public void untranslate() {
		GL.glTranslatef(-this.width / 2 + this.getX(), -this.height / 2 + this.getY(), 0);
		GL.glScalef(1f * this.width / Windows.getWindow().getWidth(), 1f * this.height / Windows.getWindow().getHeight(), 1);
	}
	public int WindowToCameraX(final int x) {
		return x - this.width / 2;
	}
	
	public int WindowToCameraY(final int y) {
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
