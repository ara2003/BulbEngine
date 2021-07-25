package com.greentree.graphics.texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;

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
		if(!glIsEnabled(GL_TEXTURE_2D))
			glEnable(GL_TEXTURE_2D);
		textureID = glGenTextures();
		if(textureID == 0)throw new UnsupportedOperationException(String.format("texture not createed %dx%d OpenGL error : %d", width, height, GL11.glGetError()));
		this.width     = width;
		this.height    = height;
		texWidth  = (width + 0.0f) / Mathf.get2Fold(width);
		texHeight = (height + 0.0f) / Mathf.get2Fold(height);
		setMinFilter(Filtering.NEAREST);
		System.out.println("sys : " + textureID);
	}

	public static void unbindTexture() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public final void bind() {
		glBindTexture(GL_TEXTURE_2D, textureID);
	}

	public void delete() {

		GL11.glDeleteTextures(textureID);

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
		return glGetInteger(GL11.GL_TEXTURE_2D) == textureID;
	}

	public void setFilter(Filtering filtering) {
		setMagFilter(filtering);
		setMinFilter(filtering);
	}

	public final void setMagFilter(final Filtering p0) {
		bind();
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Decoder.glEnum(p0));
		unbind();
	}

	public final void setMinFilter(final Filtering p0) {
		bind();
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Decoder.glEnum(p0));
		unbind();
	}

	public void setWrap(final Wrapping wrap) {
		setWrapX(wrap);
		setWrapY(wrap);
	}

	public void setWrapX(final Wrapping wrap) {
		glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, Decoder.glEnum(wrap));
	}

	public void setWrapY(final Wrapping wrap) {
		glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, Decoder.glEnum(wrap));
	}

	@Override
	public String toString() {
		return "GLTexture2D [width=" + width + ", height=" + height + ", texWidth=" + texWidth + ", texHeight="
				+ texHeight + ", textureID=" + textureID + "]";
	}

	public final void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
