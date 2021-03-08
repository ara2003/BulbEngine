package com.greentree.engine.component;

import com.greentree.engine.opengl.Texture;
import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;

public class SpriteRendener extends RendenerComponent {
	
	private static final long serialVersionUID = 1L;
	private transient Transform position;
	@EditorData
	private int width, height;
	@EditorData(name = "image")
	protected Texture texture;
	
	private final static SGL GL = Renderer.get();
	
	@Override
	public void render() {
		texture.bind();
		GL.glTranslatef(position.x, position.y, 0.0f);
		GL.glBegin(7);
		final float w = .5f / width, h = .5f / height;
		GL.glTexCoord2f(w, h);
		GL.glVertex3f(-width / 2, -height / 2, 0);
		GL.glTexCoord2f(w, texture.getHeight() - h);
		GL.glVertex3f(-width / 2, height / 2, 0);
		GL.glTexCoord2f(texture.getWidth() - w, texture.getHeight() - h);
		GL.glVertex3f(width / 2, height / 2, 0);
		GL.glTexCoord2f(texture.getWidth() - w, h);
		GL.glVertex3f(width / 2, -height / 2, 0);
		GL.glEnd();
		GL.glTranslatef(-position.x, -position.y, 0.0f);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	protected void start() {
		position = getComponent(Transform.class);
		if(width == 0) width = texture.getImageWidth();
		if(height == 0) height = texture.getImageHeight();
	}
}
