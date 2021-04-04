package com.greentree.bulbgl;

import java.nio.ByteBuffer;

import com.greentree.bulbgl.texture.Texture.Filtering;
import com.greentree.bulbgl.texture.Texture.PixelFormat;
import com.greentree.bulbgl.texture.Texture.Wrapping;
import com.greentree.bulbgl.texture.Texture1D;
import com.greentree.bulbgl.texture.Texture2D;
import com.greentree.bulbgl.texture.Texture3D;;

/** @author Arseny Latyshev */
public interface TextureLoaderI {
	
	boolean canTextureMirrorClamp();
	int getFiltering(Filtering filter);
	int getPixelFormat(PixelFormat dpixelFormat);
	Texture1D getTexture1D(String value);
	Texture2D getTexture2D(String value);
	Texture3D getTexture3D(String value);
	Texture1D getTexture1D(int x);
	Texture2D getTexture2D(int x, int y);
	Texture3D getTexture3D(int x, int y, int z);
	int getWrapping(Wrapping wrapping);
	
	void texImage2D(final int target, final int level, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final ByteBuffer p8);
	
	default void texImage2D(final int glTexture2d, final int p1, final PixelFormat dstPixelFormat, final int width, final int height, final int p5, final int srcPixelFormat,
		final int p7, final ByteBuffer imageToByteBuffer) {
		this.texImage2D(glTexture2d, p1, this.getPixelFormat(dstPixelFormat), width, height, p5, srcPixelFormat, p7, imageToByteBuffer);
	}
	
	void glTexParameteri(final int p0, final int p1, final int p2);
	
}
