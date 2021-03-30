package com.greentree.engine.component;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.image.Texture;
import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.bulbgl.opengl.rendener.SGL;

public class SpriteRendener extends AbstractRendenerComponent {
	
	private static final long serialVersionUID = 1L;
	private final static SGL GL = Renderer.get();
	private transient Transform position;
	@EditorData
	private int width, height;
	@EditorData(name = "image")
	protected Texture texture;
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public void render() {
		Color.white.bind();
		this.texture.bind();
		this.texture.setTextureFilter(SGL.GL_LINEAR);
		SpriteRendener.GL.glTranslatef(this.position.x(), this.position.y(), 0);
		SpriteRendener.GL.glBegin(SGL.GL_QUADS);
		final float w = .5f / this.width, h = .5f / this.height;
		SpriteRendener.GL.glTexCoord2f(w, h);
		SpriteRendener.GL.glVertex3f(-this.width / 2, -this.height / 2, 0);
		SpriteRendener.GL.glTexCoord2f(w, this.texture.getHeight() - h);
		SpriteRendener.GL.glVertex3f(-this.width / 2, this.height / 2, 0);
		SpriteRendener.GL.glTexCoord2f(this.texture.getWidth() - w, this.texture.getHeight() - h);
		SpriteRendener.GL.glVertex3f(this.width / 2, this.height / 2, 0);
		SpriteRendener.GL.glTexCoord2f(this.texture.getWidth() - w, h);
		SpriteRendener.GL.glVertex3f(this.width / 2, -this.height / 2, 0);
		SpriteRendener.GL.glEnd();
		SpriteRendener.GL.glTranslatef(-this.position.x(), -this.position.y(), 0);
	}
	
	public void setHeight(final int height) {
		if(height <= 0) this.height = this.texture.getImageHeight();
		else this.height = height;
	}
	
	public void setSize(final int width, final int height) {
		this.width  = width;
		this.height = height;
	}
	
	public void setWidth(final int width) {
		if(width <= 0) this.width = this.texture.getImageWidth();
		else this.width = width;
	}
	
	@Override
	protected void start() {
		this.position = this.getComponent(Transform.class);
		if(this.texture == null) throw new NullPointerException("Texture " + this.texture.getTextureRef() + " don\'t load");
		if(this.width == 0) this.width = this.texture.getImageWidth();
		if(this.height == 0) this.height = this.texture.getImageHeight();
	}
}
