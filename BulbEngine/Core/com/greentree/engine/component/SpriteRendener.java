package com.greentree.engine.component;

import java.io.IOException;

import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.opengl.InternalTextureLoader;
import com.greentree.engine.opengl.Texture;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.geom.AABB;

public class SpriteRendener extends RendenerComponent {
	
	private static final long serialVersionUID = 1L;
	
	@EditorData(name="image")
	private String ref;
	private transient Transform t;
	@EditorData
	private int width, height;
	protected Texture texture;
	
	
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
		try {
			texture = InternalTextureLoader.get().getTexture(ref, false, 9728, null);
		}catch(IOException e) {
			e.printStackTrace();
		}
		if(width == 0)width = texture.getImageWidth();
		if(height == 0)height = texture.getImageHeight();
	}

	@Override
	public void draw(SGL gl) {
		texture.bind();
		gl.glTranslatef(t.x, t.y, 0.0f);
		gl.glBegin(7);
		final float w = .5f / width, h = .5f / height;
		gl.glTexCoord2f(w, h);
		gl.glVertex3f(-width/2, -height/2, 0);
		gl.glTexCoord2f(w, texture.getHeight() - h);
		gl.glVertex3f(-width/2, height/2, 0);
		gl.glTexCoord2f(texture.getWidth() - w, texture.getHeight() - h);
		gl.glVertex3f(width/2, height/2, 0);
		gl.glTexCoord2f(texture.getWidth() - w, h);
		gl.glVertex3f(width/2, -height/2, 0);
		gl.glEnd();
		gl.glTranslatef(-t.x, -t.y, 0.0f);
	}

	@Override
	protected AABB getAABB() {
		return new AABB(t.x-width/2, t.y-height/2, width, height);
	}

}
