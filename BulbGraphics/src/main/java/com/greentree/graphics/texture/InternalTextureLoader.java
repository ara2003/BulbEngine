package com.greentree.graphics.texture;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

import com.greentree.common.math.Mathf;
import com.greentree.data.loading.ResourceLoader;

public class InternalTextureLoader {
	
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
	
	public GLTexture2D getTexture(final InputStream in, final ImageType type) throws IOException {
		final LoadableImageData imageData     = type.get();
		final ByteBuffer        textureBuffer = imageData.loadImage(new BufferedInputStream(in), false, null);
		final int               width         = imageData.getWidth();
		final int               height        = imageData.getHeight();
		final boolean           hasAlpha      = imageData.getDepth() == 32; 
		final GLTexture2D       texture       = new GLTexture2D(width, height);
		texture.bind();
		final int srcPixelFormat = hasAlpha ? GL11.GL_RGBA : GL11.GL_RGB;
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, dstPixelFormat, Mathf.get2Fold(width),
			Mathf.get2Fold(height), 0, srcPixelFormat, GL11.GL_UNSIGNED_BYTE, textureBuffer);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}
	
	public GLTexture2D getTexture(final String resourceName) {
		try {
    		return this.getTexture(ResourceLoader.getResourceAsStream(resourceName), ImageType.getImageType(resourceName));
    	}catch(IOException e) {
    		throw new IllegalArgumentException(resourceName, e);
    	}
	}
	
	public void set16BitMode() {
		dstPixelFormat = GL11.GL_RGBA16;
	}
	
}
