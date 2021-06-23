package com.greentree.graphics.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import com.greentree.graphics.GLType;
import com.greentree.graphics.PixelFormat;
import com.greentree.graphics.Wrapping;
import com.greentree.graphics.texture.Filtering;
import com.greentree.graphics.texture.GLTexture2D;
import com.greentree.graphics.texture.GLTextureLoader;

public class BufferedImageUtil {

	private BufferedImageUtil() {
	}

	public static GLTexture2D getTexture(final BufferedImage resourceImage) throws IOException {
		return getTexture(resourceImage, Filtering.LINEAR);
	}

	public static GLTexture2D getTexture(final BufferedImage resourceImage, final Filtering filter)
			throws IOException {
		return getTexture(resourceImage, PixelFormat.RGBA, filter, filter);
	}

	public static GLTexture2D getTexture(final BufferedImage bufferedImage,
			final PixelFormat dstPixelFormat, final Filtering minFilter, final Filtering magFilter) throws IOException {

		final ImageIOImageData data = new ImageIOImageData();
		PixelFormat            srcPixelFormat;
		final ByteBuffer data1 = data.imageToByteBuffer(bufferedImage, false, false, null);//не вносить в GLTextureLoader.glTexImage2D
		
		final GLTexture2D texture = GLTextureLoader.getTexture2D(bufferedImage.getWidth(), bufferedImage.getHeight());

		texture.bind();

		texture.setWrapX(Wrapping.CLAMP_TO_BORDER);
		texture.setWrapY(Wrapping.CLAMP_TO_BORDER);

		if(bufferedImage.getColorModel().hasAlpha()) srcPixelFormat = PixelFormat.RGBA;
		else srcPixelFormat = PixelFormat.RGB;

		GLTextureLoader.glTexImage2D(0, dstPixelFormat, data.getWidth(), data.getHeight(),
				srcPixelFormat, GLType.UNSIGNED_BYTE, data1);

		GLTexture2D.unbindTexture();

		texture.setMagFilter(magFilter);
		texture.setMinFilter(minFilter);

		return texture;
	}
}
