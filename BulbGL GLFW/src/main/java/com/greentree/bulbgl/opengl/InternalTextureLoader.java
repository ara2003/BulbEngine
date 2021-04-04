package com.greentree.bulbgl.opengl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.TextureLoaderI;
import com.greentree.bulbgl.texture.Texture;
import com.greentree.bulbgl.texture.Texture2D;
import com.greentree.common.loading.ResourceLoader;
import com.greentree.common.math.Mathf;

public class InternalTextureLoader {
	
	protected static TextureLoaderI GL = BulbGL.getTextureLoader();
	private static final InternalTextureLoader loader = new InternalTextureLoader();
	
	private int dstPixelFormat = GL11.GL_RGBA;
	
	public static IntBuffer createIntBuffer(final int size) {
		final ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());
		return temp.asIntBuffer();
	}
	
	public static InternalTextureLoader get() {
		return InternalTextureLoader.loader;
	}

	public Texture getTexture(final File source, final boolean flipped, final int filter) throws IOException {
		return this.getTexture(source, flipped, null);
	}
	
	public Texture getTexture(final File source, final boolean flipped, final int[] transparent)
		throws IOException {
		final String      resourceName = source.getAbsolutePath();
		final InputStream in           = new FileInputStream(source);
		return this.getTexture(in, resourceName, flipped, transparent);
	}
	
	private Texture2D getTexture(final InputStream in, final String resourceName, final boolean flipped, final int[] transparent)
		throws IOException {
		final LoadableImageData imageData     = ImageDataFactory.getImageDataFor(resourceName);
		final ByteBuffer        textureBuffer = imageData.loadImage(new BufferedInputStream(in), flipped, transparent);
		final int               width         = imageData.getWidth();
		final int               height        = imageData.getHeight();
		final boolean           hasAlpha      = imageData.getDepth() == 32;
		final Texture2D         texture       = new GLTexture2D(width, height);
		texture.bind();
		final int srcPixelFormat = hasAlpha ? 6408 : 6407;
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, this.dstPixelFormat, Mathf.get2Fold(width),
			Mathf.get2Fold(height), 0, srcPixelFormat, GL11.GL_UNSIGNED_BYTE, textureBuffer);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}
	
	public Texture2D getTexture(final String resourceName) throws IOException {
		return getTexture(ResourceLoader.getResourceAsStream(resourceName), resourceName);
	}
	
	private Texture2D getTexture(InputStream resourceAsStream, String resourceName) throws IOException {
		return getTexture(resourceAsStream, resourceName, false, null);
	}

	public void set16BitMode() {
		this.dstPixelFormat = GL11.GL_RGBA16;
	}
	
}
