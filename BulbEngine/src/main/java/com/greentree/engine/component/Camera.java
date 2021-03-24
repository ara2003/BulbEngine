package com.greentree.engine.component;

import org.joml.Vector2f;
import org.joml.Vector2fc;

import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.bulbgl.opengl.rendener.SGL;
import com.greentree.engine.Game;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.system.NecessarilySystems;
import com.greentree.engine.system.RenderSystem;

/** @author Arseny Latyshev */
@NecessarilySystems({RenderSystem.class})
@RequireComponent({Transform.class})
public class Camera extends GameComponent {
	
	private static final SGL GL = Renderer.get();
	@EditorData(def = "window::width")
	private int width;
	@EditorData(def = "window::height")
	private int height;
	private Transform position;
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	@Override
	protected void start() {
		position = getComponent(Transform.class);
	}
	
	public void translate() {
		GL.glScalef(1f * Game.getWindow().getWidth() / width, 1f * Game.getWindow().getHeight() / height, 1);
		GL.glTranslatef(width / 2 - getX(), height / 2 - getY(), 0);
	}
	
	public void untranslate() {
		GL.glTranslatef(-width / 2 + getX(), -height / 2 + getY(), 0);
		GL.glScalef(1f * width / Game.getWindow().getWidth(), 1f * height / Game.getWindow().getHeight(), 1);
	}

	public float getX() {
		return position.x;
	}
	public float getY() {
		return position.y;
	}

	public Vector2f getUVPosition(Vector2fc position) {
		return WorldToCamera(position).mul(2).div(width, height);
	}

	public Vector2f WorldToCamera(Vector2fc position) {
		Vector2f vec = new Vector2f(position);
		vec.x *= 1f * Game.getWindow().getWidth() / width;
		vec.x -= this.position.x;
		vec.y *= 1f * Game.getWindow().getHeight() / height;
		vec.y -= this.position.y;
		return vec;
	}
	public Vector2f getWorldPosition(Vector2fc position) {
		Vector2f vec = new Vector2f(position);
		vec.x += this.position.x;
		vec.x /= 1f * Game.getWindow().getWidth() / width;
		vec.y += this.position.y;
		vec.y /= 1f * Game.getWindow().getHeight() / height;
		return vec;
	}

	public int WindowToCameraX(int x) {
		return x - width / 2;
	}

	public int WindowToCameraY(int y) {
		return height / 2 - y;
	}

}
