package com.greentree.bulbgl.opengl;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;

import com.greentree.bulbgl.TextureLoaderI;
import com.greentree.bulbgl.opengl.texture.GLTexture2D;
import com.greentree.bulbgl.opengl.texture.InternalTextureLoader;
import com.greentree.bulbgl.texture.Texture.Filtering;
import com.greentree.bulbgl.texture.Texture.PixelFormat;
import com.greentree.bulbgl.texture.Texture.Wrapping;
import com.greentree.bulbgl.texture.Texture1D;
import com.greentree.bulbgl.texture.Texture2D;
import com.greentree.bulbgl.texture.Texture3D;


/** @author Arseny Latyshev */
public class GLTextureLoader implements TextureLoaderI {
	
	public final static int getGLFilltering(final Filtering f) {
		return switch(f) {
			case LINEAR -> GL11.GL_LINEAR;
			case NEAREST -> GL11.GL_NEAREST;
		};
	}
	
	private static int getGLPixelFormat(final PixelFormat pixelFormat) {
		return switch(pixelFormat) {
			case RGB -> GL11.GL_RGB;
			case RGBA -> GL11.GL_RGBA;
		};
	}
	
	public final static int getGLWraping(final Wrapping wrap) {
		return switch(wrap) {
			case REPEAT -> GL11.GL_REPEAT;
			case MIRRORED_REPEAT -> GL14.GL_MIRRORED_REPEAT;
			case CLAMP_TO_BORDER -> GL13.GL_CLAMP_TO_BORDER;
			case CLAMP_TO_EDGE -> GL12.GL_CLAMP_TO_EDGE;
		};
	}
	
	@Override
	public boolean canTextureMirrorClamp() {
		return true;
	}
	
	@Override
	public int getFiltering(final Filtering dstPixelFormat) {
		return GLTextureLoader.getGLFilltering(dstPixelFormat);
	}
	
	@Override
	public int getPixelFormat(final PixelFormat pixelFormat) {
		return GLTextureLoader.getGLPixelFormat(pixelFormat);
	}
	
	@Override
	public Texture1D getTexture1D(final int x) {
		return null;
	}
	
	@Override
	public Texture1D getTexture1D(final String value) {
		return null;
	}
	
	@Override
	public Texture2D getTexture2D(final int x, final int y) {
		final GLTexture2D texture = new GLTexture2D(x, y);
		texture.bind();
		float borderColor[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}
	
	@Override
	public Texture2D getTexture2D(final String value) {
		try {
			return InternalTextureLoader.get().getTexture(value);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Texture3D getTexture3D(final int x, final int y, final int z) {
		return null;
	}
	
	@Override
	public Texture3D getTexture3D(final String value) {
		return null;
	}
	
	@Override
	public int getWrapping(final Wrapping dstPixelFormat) {
		return GLTextureLoader.getGLWraping(dstPixelFormat);
	}
	
	@Override
	public void glTexParameteri(final int target, final int param, final int value) {
		GL11.glTexParameteri(target, param, value);
	}
	
	@Override
	public void texImage2D(final int target, final int i, final int dstPixelFormat, final int width, final int height,
		final int j, final int srcPixelFormat, final int glUnsignedByte, final ByteBuffer textureBuffer) {
		GL11.glTexImage2D(target, i, dstPixelFormat, width, height, j, srcPixelFormat, glUnsignedByte, textureBuffer);
	}
	
}
