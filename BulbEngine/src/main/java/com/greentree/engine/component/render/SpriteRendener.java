package com.greentree.engine.component.render;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.bulbgl.opengl.Graphics;
import com.greentree.bulbgl.texture.Texture;
import com.greentree.bulbgl.texture.Texture.Filtering;
import com.greentree.bulbgl.texture.Texture2D;
import com.greentree.engine.Cameras;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.component.EditorData;

public class SpriteRendener extends CameraRendenerComponent {
	
	private final static GraphicsI GL = BulbGL.getGraphics();
	private transient Transform position;
	@EditorData
	private float width, height;
	@EditorData(name = "image")
	protected Texture2D texture;
	
	public float getHeight() {
		return this.height;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	@Override
	public void render() {
		this.texture.bind();
		SpriteRendener.GL.glTranslatef(this.position.x(), this.position.y(), this.position.z());
		Color.white.bind();
		final float w = .5f / this.getWidth(), h = .5f / this.getHeight();
		SpriteRendener.GL.glBegin(GraphicsI.GL_QUADS);
		SpriteRendener.GL.glTexCoord2f(w, h);
		SpriteRendener.GL.glVertex2f(0, 0);
		SpriteRendener.GL.glTexCoord2f(w, this.texture.getTexHeight() - h);
		SpriteRendener.GL.glVertex2f(0, this.height);
		SpriteRendener.GL.glTexCoord2f(this.texture.getTexWidth() - w, this.texture.getTexHeight() - h);
		SpriteRendener.GL.glVertex2f(this.width, this.height);
		SpriteRendener.GL.glTexCoord2f(this.texture.getTexWidth() - w, h);
		SpriteRendener.GL.glVertex2f(this.width, 0);
		SpriteRendener.GL.glEnd();
		Cameras.getMainCamera().translate();
		Color.red.bind();
		Graphics.drawOval(0, 0, width, height);
		Cameras.getMainCamera().untranslate();
		SpriteRendener.GL.glTranslatef(-this.position.x(), -this.position.y(), -this.position.z());
		BulbGL.getGraphics().unbindTexture();
	}
	
	public void setHeight(final float height) {
		if(height <= 0) this.height = this.texture.getHeight();
		else this.height = height;
	}
	
	public void setSize(final float width, final float height) {
		this.width  = width;
		this.height = height;
	}
	
	public void setWidth(final float width) {
		if(width <= 0) this.width = this.texture.getWidth();
		else this.width = width;
	}
	
	@Override
	protected void start() {
		this.texture.setMagFilter(Filtering.LINEAR);
		this.texture.setMinFilter(Filtering.NEAREST);
		
		this.texture.setWrap(Texture.Wrapping.CLAMP_TO_BORDER);
		
		this.position = this.getComponent(Transform.class);
		if(this.texture == null) System.out.println(this.getObject());
		if(this.width == 0) this.width = this.texture.getWidth();
		if(this.height == 0) this.height = this.texture.getHeight();
	}
}
