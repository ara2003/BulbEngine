package com.greentree.engine.component;

import org.joml.Vector2f;

import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.bulbgl.opengl.rendener.SGL;
import com.greentree.engine.Game;
import com.greentree.engine.system.NecessarilySystems;
import com.greentree.engine.system.RenderSystem;

/** @author Arseny Latyshev */
@NecessarilySystems({RenderSystem.class})
@RequireComponent({Transform.class})
public class Camera extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	private static final SGL GL = Renderer.get();
	@EditorData
	private int width, height;
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
		GL.glTranslatef(width / 2 - position.x, height / 2 - position.y, 0);
	}
	
	public void untranslate() {
		GL.glTranslatef(-width / 2 + position.x, -height / 2 + position.y, 0);
		GL.glScalef(1f * width / Game.getWindow().getWidth(), 1f * height / Game.getWindow().getHeight(), 1);
	}

	public float getX() {
		return position.x;
	}
	public float getY() {
		return position.y;
	}
	
	public Vector2f getPosition(Vector2f position) {
		Vector2f vec = new Vector2f(position);
		vec.x *= 1f * Game.getWindow().getWidth() / width;
		vec.x += width / 2 - position.x;
		vec.y *= 1f * Game.getWindow().getHeight() / width;
		vec.y += height / 2 - position.y;
		return vec;
	}

}
