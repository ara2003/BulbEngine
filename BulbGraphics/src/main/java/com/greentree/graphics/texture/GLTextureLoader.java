package com.greentree.graphics.texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BORDER_COLOR;
import static org.lwjgl.opengl.GL11.glTexParameterfv;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import com.greentree.graphics.GLType;
import com.greentree.graphics.PixelFormat;
import com.greentree.graphics.core.Decoder;


/** @author Arseny Latyshev */
public abstract class GLTextureLoader {
	
	public static GLTexture2D getTexture2D(final int x, final int y) {
		final GLTexture2D texture = new GLTexture2D(x, y);
		texture.bind();
		float borderColor[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}
	
	public static GLTexture2D getTexture2D(final String value) {
		return InternalTextureLoader.get().getTexture(value);
	}

	public static void glTexImage2D(int mipmapLevel, PixelFormat dstPixelFormat, int width, int height, PixelFormat srcPixelFormat, GLType type, ByteBuffer data) {
		GL11.glTexImage2D(GL_TEXTURE_2D, mipmapLevel, Decoder.glEnum(dstPixelFormat), width, height, 0, Decoder.glEnum(srcPixelFormat), type.glEnum(), data);
	}
	
}
