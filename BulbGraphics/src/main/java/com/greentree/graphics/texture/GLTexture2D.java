package com.greentree.graphics.texture;

import org.lwjgl.opengl.GL11;

import com.greentree.common.math.Mathf;
import com.greentree.graphics.Wrapping;
import com.greentree.graphics.core.Decoder;


/** @author Arseny Latyshev */
public class GLTexture2D {

	private final int width;
	private final int height;
	private final float texWidth;
	private final float texHeight;
	private final int textureID;

	public GLTexture2D(final int width, final int height) {
		if(!GL11.glIsEnabled(GL11.GL_TEXTURE_2D))
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		textureID = GL11.glGenTextures();
		if(textureID == 0)throw new UnsupportedOperationException(String.format("texture not createed %dx%d", width, height));
		this.width     = width;
		this.height    = height;
		texWidth  = (width + 0.0f) / Mathf.get2Fold(width);
		texHeight = (height + 0.0f) / Mathf.get2Fold(height);
	}

	public static void unbindTexture() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public final void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	public final int getHeight() {
		return height;
	}

	public final float getTexHeight() {
		return texHeight;
	}

	public final float getTexWidth() {
		return texWidth;
	}

	public final int getWidth() {
		return width;
	}

	public boolean isBind() {//TODO test
		return GL11.glGetInteger(GL11.GL_TEXTURE_2D) == textureID;
	}

	public void setFilter(Filtering filtering) {
		setMagFilter(filtering);
		setMinFilter(filtering);
	}

	public final void setMagFilter(final Filtering p0) {
		bind();
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Decoder.glEnum(p0));
		unbind();
	}

	public final void setMinFilter(final Filtering p0) {
		bind();
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Decoder.glEnum(p0));
		unbind();
	}

	public void setWrap(final Wrapping wrap) {
		setWrapX(wrap);
		setWrapY(wrap);
	}

	public void setWrapX(final Wrapping wrap) {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, Decoder.glEnum(wrap));
	}

	public void setWrapY(final Wrapping wrap) {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, Decoder.glEnum(wrap));
	}

	@Override
	public String toString() {
		return "GLTexture2D [width=" + width + ", height=" + height + ", texWidth=" + texWidth + ", texHeight="
				+ texHeight + ", textureID=" + textureID + "]";
	}

	public final void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

}
