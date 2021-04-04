package com.greentree.engine.component.render;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.bulbgl.texture.Texture;
import com.greentree.bulbgl.texture.Texture.Filtering;
import com.greentree.bulbgl.texture.Texture2D;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.component.EditorData;

public class SpriteRendener extends CameraRendenerComponent {
	
	private final static GraphicsI GL = BulbGL.getGraphics();
	private transient Transform position;
	@EditorData
	private int width, height;
	@EditorData(name = "image")
	protected Texture2D texture;
	
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
		SpriteRendener.GL.glTranslatef(this.position.x(), this.position.y(), 0);
		final float w = .5f / getWidth(), h = .5f / getHeight();
		SpriteRendener.GL.glBegin(GraphicsI.GL_QUADS);
		SpriteRendener.GL.glTexCoord2f(w, h);
		SpriteRendener.GL.glVertex3f(0, 0, 0.0f);
		SpriteRendener.GL.glTexCoord2f(w, (texture.getTexHeight()) - h);
		SpriteRendener.GL.glVertex3f(0, height, 0.0f);
		SpriteRendener.GL.glTexCoord2f((texture.getTexWidth()) - w, (texture.getTexHeight()) - h);
		SpriteRendener.GL.glVertex3f(width, height, 0.0f);
		SpriteRendener.GL.glTexCoord2f((texture.getTexWidth()) - w, h);
		SpriteRendener.GL.glVertex3f(width, 0, 0.0f);
		SpriteRendener.GL.glEnd();
		SpriteRendener.GL.glTranslatef(-this.position.x(), -this.position.y(), 0);
		BulbGL.getGraphics().unbindTexture();
	}
	
	public void setHeight(final int height) {
		if(height <= 0) this.height = this.texture.getHeight();
		else this.height = height;
	}
	
	public void setSize(final int width, final int height) {
		this.width  = width;
		this.height = height;
	}
	
	public void setWidth(final int width) {
		if(width <= 0) this.width = this.texture.getWidth();
		else this.width = width;
	}
	
	@Override
	protected void start() {
		
		
		this.texture.setMagFilter(Filtering.LINEAR);
		this.texture.setMinFilter(Filtering.NEAREST);

		texture.setWrap(Texture.Wrapping.CLAMP_TO_BORDER);
		
		this.position = this.getComponent(Transform.class);
		if(texture == null)System.out.println(getObject());
		if(this.width == 0) this.width = this.texture.getWidth();
		if(this.height == 0) this.height = this.texture.getHeight();
	}
}
