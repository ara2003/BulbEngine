//
// Decompiled by Procyon v0.5.36
//
package com.greentree.bulbgl.opengl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.BufferUtils;

public class TGAImageData implements LoadableImageData {
	
	private int height;
	private short pixelDepth;
	private int texHeight;
	private int texWidth;
	private int width;
	
	@Override
	public void configureEdging(final boolean edging) {
	}
	
	private short flipEndian(final short signedShort) {
		final int input = signedShort & 0xFFFF;
		return (short) ((input << 8) | ((input & 0xFF00) >>> 8));
	}
	
	private int get2Fold(final int fold) {
		int ret;
		for(ret = 2; ret < fold; ret *= 2) {
		}
		return ret;
	}
	
	@Override
	public int getDepth() {
		return pixelDepth;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public ByteBuffer getImageBufferData() {
		throw new RuntimeException("TGAImageData doesn't store it's image.");
	}
	
	@Override
	public int getTexHeight() {
		return texHeight;
	}
	
	@Override
	public int getTexWidth() {
		return texWidth;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis) throws IOException {
		return this.loadImage(fis, true, null);
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis, boolean flipped, boolean forceAlpha, final int[] transparent)
			throws IOException {
		if(transparent != null) forceAlpha = true;
		byte red = 0;
		byte green = 0;
		byte blue = 0;
		byte alpha = 0;
		final BufferedInputStream bis = new BufferedInputStream(fis, 100000);
		final DataInputStream dis = new DataInputStream(bis);
		final short idLength = (short) dis.read();
		dis.read();
		final short imageType = (short) dis.read();
		flipEndian(dis.readShort());
		flipEndian(dis.readShort());
		dis.read();
		flipEndian(dis.readShort());
		flipEndian(dis.readShort());
		if(imageType != 2) throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
		width = flipEndian(dis.readShort());
		height = flipEndian(dis.readShort());
		pixelDepth = (short) dis.read();
		if(pixelDepth == 32) forceAlpha = false;
		texWidth = get2Fold(width);
		texHeight = get2Fold(height);
		final short imageDescriptor = (short) dis.read();
		if((imageDescriptor & 0x20) == 0x0) flipped = !flipped;
		if(idLength > 0) bis.skip(idLength);
		byte[] rawData = null;
		if((pixelDepth == 32) || forceAlpha) {
			pixelDepth = 32;
			rawData = new byte[texWidth * texHeight * 4];
		}else {
			if(pixelDepth != 24) throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
			rawData = new byte[texWidth * texHeight * 3];
		}
		if(pixelDepth == 24) {
			if(flipped) for(int i = height - 1; i >= 0; --i) for(int j = 0; j < width; ++j) {
				blue = dis.readByte();
				green = dis.readByte();
				red = dis.readByte();
				final int ofs = (j + (i * texWidth)) * 3;
				rawData[ofs] = red;
				rawData[ofs + 1] = green;
				rawData[ofs + 2] = blue;
			}
			else for(int i = 0; i < height; ++i) for(int j = 0; j < width; ++j) {
				blue = dis.readByte();
				green = dis.readByte();
				red = dis.readByte();
				final int ofs = (j + (i * texWidth)) * 3;
				rawData[ofs] = red;
				rawData[ofs + 1] = green;
				rawData[ofs + 2] = blue;
			}
		}else if(pixelDepth == 32) if(flipped) for(int i = height - 1; i >= 0; --i) for(int j = 0; j < width; ++j) {
			blue = dis.readByte();
			green = dis.readByte();
			red = dis.readByte();
			if(forceAlpha) alpha = -1;
			else alpha = dis.readByte();
			final int ofs = (j + (i * texWidth)) * 4;
			rawData[ofs] = red;
			rawData[ofs + 1] = green;
			rawData[ofs + 2] = blue;
			rawData[ofs + 3] = alpha;
			if(alpha == 0) {
				rawData[ofs + 2] = 0;
				rawData[ofs] = rawData[ofs + 1] = 0;
			}
		}
		else for(int i = 0; i < height; ++i) for(int j = 0; j < width; ++j) {
			blue = dis.readByte();
			green = dis.readByte();
			red = dis.readByte();
			if(forceAlpha) alpha = -1;
			else alpha = dis.readByte();
			final int ofs = (j + (i * texWidth)) * 4;
			if(ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
				rawData[ofs] = red;
				rawData[ofs + 1] = green;
				rawData[ofs + 2] = blue;
				rawData[ofs + 3] = alpha;
			}else {
				rawData[ofs] = red;
				rawData[ofs + 1] = green;
				rawData[ofs + 2] = blue;
				rawData[ofs + 3] = alpha;
			}
			if(alpha == 0) {
				rawData[ofs + 2] = 0;
				rawData[ofs] = rawData[ofs + 1] = 0;
			}
		}
		fis.close();
		if(transparent != null) for(int i = 0; i < rawData.length; i += 4) {
			boolean match = true;
			for(int c = 0; c < 3; ++c) if(rawData[i + c] != transparent[c]) match = false;
			if(match) rawData[i + 3] = 0;
		}
		final ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
		scratch.put(rawData);
		final int perPixel = pixelDepth / 8;
		if(height < (texHeight - 1)) {
			final int topOffset = (texHeight - 1) * texWidth * perPixel;
			final int bottomOffset = (height - 1) * texWidth * perPixel;
			for(int x = 0; x < (texWidth * perPixel); ++x) {
				scratch.put(topOffset + x, scratch.get(x));
				scratch.put(bottomOffset + (texWidth * perPixel) + x, scratch.get((texWidth * perPixel) + x));
			}
		}
		if(width < (texWidth - 1)) for(int y = 0; y < texHeight; ++y) for(int k = 0; k < perPixel; ++k) {
			scratch.put((((y + 1) * texWidth * perPixel) - perPixel) + k, scratch.get((y * texWidth * perPixel) + k));
			scratch.put((y * texWidth * perPixel) + (width * perPixel) + k,
					scratch.get((y * texWidth * perPixel) + ((width - 1) * perPixel) + k));
		}
		scratch.flip();
		return scratch;
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis, final boolean flipped, final int[] transparent)
			throws IOException {
		return this.loadImage(fis, flipped, false, transparent);
	}
}
