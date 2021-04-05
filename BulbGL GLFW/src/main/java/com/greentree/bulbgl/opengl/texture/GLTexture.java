package com.greentree.bulbgl.opengl.texture;

import org.lwjgl.opengl.GL11;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.opengl.GLTextureLoader;
import com.greentree.bulbgl.texture.Texture;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class GLTexture implements Texture {

	private final int textureID;
	
	public GLTexture(int textureID) {
		if(!GL11.glIsEnabled(getTextureTarget()))
		BulbGL.getGraphics().glEnable(getTextureTarget());
		this.textureID = textureID;
	}
	
	@Override
	public final void bind() {
		GL11.glBindTexture(getTextureTarget(), textureID);
	}
	
	@Override
	public final void setMinFilter(Filtering p0) {
		bind();
		GL11.glTexParameterf(getTextureTarget(), GL11.GL_TEXTURE_MIN_FILTER, GLTextureLoader.getGLFilltering(p0));
		unbind();
	}

	public final void unbind() {
		GL11.glBindTexture(getTextureTarget(), 0);
	}
	
	@Override
	public final void setMagFilter(Filtering p0) {
		bind();
		GL11.glTexParameterf(getTextureTarget(), GL11.GL_TEXTURE_MAG_FILTER, GLTextureLoader.getGLFilltering(p0));
		unbind();
	}
	
	protected abstract int getTextureTarget();
	
}
