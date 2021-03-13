package com.greentree.engine.component;

import com.greentree.engine.Game;
import com.greentree.engine.opengl.Renderable;
import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.RenderSystem;
import com.greentree.engine.system.necessarilySystems;

/** @author Arseny Latyshev */
@necessarilySystems({RenderSystem.class})
public class Camera extends offsetGameComponent {
	
	private static final long serialVersionUID = 1L;
	private static final SGL GL = Renderer.get();
	@EditorData
	private int width, height;
	private Transform position;
	
	public void draw() {
		translate();
		Game.getMainNode().getAllComponents(Renderable.class).forEach(e->((Renderable) e).render());
		untranslate();
	}
	
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
}
