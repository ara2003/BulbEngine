package com.greentree.engine.component;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.lwjgl.opengl.GL11;

import com.greentree.engine.Game;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.system.NecessarilySystems;
import com.greentree.engine.system.RenderSystem;

/** @author Arseny Latyshev */
@NecessarilySystems({RenderSystem.class})
@RequireComponent({Transform.class})
public class Camera extends GameComponent {
	
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
		vec.x /= 1f * Game.getWindow().getWidth() / this.width;
		vec.y += this.position.y();
		vec.y /= 1f * Game.getWindow().getHeight() / this.height;
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
		GL11.glScalef(1f * Game.getWindow().getWidth() / this.width, 1f * Game.getWindow().getHeight() / this.height, 1);
		GL11.glTranslatef(this.width / 2 - this.getX(), this.height / 2 - this.getY(), 0);
	}
	
	public void untranslate() {
		GL11.glTranslatef(-this.width / 2 + this.getX(), -this.height / 2 + this.getY(), 0);
		GL11.glScalef(1f * this.width / Game.getWindow().getWidth(), 1f * this.height / Game.getWindow().getHeight(), 1);
	}
	public void translate(double x, double y){
		GL11.glTranslated(x, y, 0);
	}
	public int WindowToCameraX(final int x) {
		return x - this.width / 2;
	}
	
	public int WindowToCameraY(final int y) {
		return this.height / 2 - y;
	}
	
	public Vector2f WorldToCamera(final Vector2fc position) {
		final Vector2f vec = new Vector2f(position);
		vec.x *= 1f * Game.getWindow().getWidth() / this.width;
		vec.x -= this.position.x();
		vec.y *= 1f * Game.getWindow().getHeight() / this.height;
		vec.y -= this.position.y();
		return vec;
	}
	
	public void scale(double width, double height) {
		GL11.glScaled(width, height, 1);
	}
	
}
