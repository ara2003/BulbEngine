package com.greentree.bulbgl.opengl;

import org.lwjgl.opengl.GL11;

import com.greentree.bulbgl.texture.Texture2D;
import com.greentree.common.math.Mathf;


/** @author Arseny Latyshev */
public class GLTexture2D extends GLTexture implements Texture2D {
	
	private final int width;
	private final int height;
	private final float texWidth;
	private final float texHeight;
	
	public GLTexture2D(final int width, final int height) {
		this(width, height, GL11.glGenTextures());
	}
	
	public GLTexture2D(final int width, final int height, final int id) {
		super(id);
		this.width     = width;
		this.height    = height;
		this.texWidth  = (width +0.0f)/ Mathf.get2Fold(width);
		this.texHeight = (height+0.0f) / Mathf.get2Fold(height);
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public float getTexHeight() {
		return this.texHeight;
	}
	
	@Override
	protected int getTextureTarget() {
		return GL11.GL_TEXTURE_2D;
	}
	
	@Override
	public float getTexWidth() {
		return this.texWidth;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public void setWrapX(final Wrapping repeat) {
	}
	
	@Override
	public void setWrapY(final Wrapping repeat) {
	}
	
}
