package com.greentree.bulbgl.glfw;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.bulbgl.TextureLoaderI;
import com.greentree.bulbgl.opengl.ImageIOImageData;
import com.greentree.bulbgl.texture.Texture;
import com.greentree.bulbgl.texture.Texture2D;

public class BufferedImageUtil {
	
	private BufferedImageUtil() {
	}
	
	public static Texture getTexture(final BufferedImage resourceImage) throws IOException {
		final Texture tex = BufferedImageUtil.getTexture(resourceImage, Texture.Filtering.LINEAR);
		return tex;
	}
	
	public static Texture getTexture(final BufferedImage resourceImage, final Texture.Filtering filter)
			throws IOException {
		final Texture tex = BufferedImageUtil.getTexture(resourceImage, Texture.PixelFormat.RGBA, filter, filter);
		return tex;
	}
	
	public static Texture2D getTexture(final BufferedImage bufferedImage,
			final Texture.PixelFormat dstPixelFormat, final Texture.Filtering minFilter, final Texture.Filtering magFilter) throws IOException {
		TextureLoaderI TL = BulbGL.getTextureLoader();
		
		final ImageIOImageData data = new ImageIOImageData();
		int srcPixelFormat = 0;
		
		final Texture2D texture = TL.getTexture2D(data.getTexWidth(), data.getTexWidth());
		
		texture.bind();
		
		if(bufferedImage.getColorModel().hasAlpha()) srcPixelFormat = 6408;
		else srcPixelFormat = 6407;
		
		if(TL.canTextureMirrorClamp()) {
			TL.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GraphicsI.GL_MIRROR_CLAMP_TO_EDGE_EXT);
			TL.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GraphicsI.GL_MIRROR_CLAMP_TO_EDGE_EXT);
		}else {
			TL.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			TL.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		}
		
		
		
		ByteBuffer data1 = data.imageToByteBuffer(bufferedImage, false, false, null);//не вносить в TL.texImage2D
		
		TL.texImage2D(GL11.GL_TEXTURE_2D, 0, dstPixelFormat, data.getWidth(), data.getHeight(), 0,
				srcPixelFormat, 5121, data1);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		texture.setMagFilter(magFilter);
		texture.setMinFilter(minFilter);
		
		return texture;
	}
}
