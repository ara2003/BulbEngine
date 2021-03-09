package com.greentree.engine.component;

import com.greentree.engine.Game;
import com.greentree.engine.necessarilySystems;
import com.greentree.engine.opengl.rendener.LineStripRenderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.RenderSystem;

/** @author Arseny Latyshev */
@necessarilySystems({RenderSystem.class})
public class Camera extends offsetGameComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	@DiapasonInt(min = 1)
	private int width, height;
	private Transform position;
	
	public void draw(SGL GL, LineStripRenderer SLR) {
		GL.glScalef(1f * Game.getWindow().getWidth() / width, 1f * Game.getWindow().getHeight() / height, 1);
		GL.glTranslatef(width / 2 - position.x, height / 2 - position.y, 0);
		
		Game.getMainNode().getAllComponents(RendenerComponent.class).forEach(RendenerComponent::render);
		
		GL.glTranslatef(-width / 2 + position.x, -height / 2 + position.y, 0);
		GL.glScalef(1f * width / Game.getWindow().getWidth(), 1f * height / Game.getWindow().getHeight(), 1);
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
}
