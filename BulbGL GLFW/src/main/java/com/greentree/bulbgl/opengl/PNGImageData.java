package com.greentree.bulbgl.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.greentree.bulbgl.PNGDecoder;

public class PNGImageData implements LoadableImageData {

	private int bitDepth;
	private int height;
	private ByteBuffer scratch;
	private int texHeight;
	private int texWidth;
	private int width;

	@Override
	public void configureEdging(final boolean edging) {
	}

	private int get2Fold(final int fold) {
		int ret;
		for(ret = 2; ret < fold; ret *= 2) {
		}
		return ret;
	}

	@Override
	public int getDepth() {
		return bitDepth;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public ByteBuffer getImageBufferData() {
		return scratch;
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
		return this.loadImage(fis, false, null);
	}

	@Override
	public ByteBuffer loadImage(final InputStream fis, final boolean flipped, boolean forceAlpha,
			final int[] transparent) throws IOException {
		if(transparent != null) {
			forceAlpha = true;
			throw new IOException("Transparent color not support in custom PNG Decoder");
		}
		final PNGDecoder decoder = new PNGDecoder(fis);
		if(!decoder.isRGB()) throw new IOException("Only RGB formatted images are supported by the PNGLoader");
		width = decoder.getWidth();
		height = decoder.getHeight();
		texWidth = get2Fold(width);
		texHeight = get2Fold(height);
		final int perPixel = decoder.hasAlpha() ? 4 : 3;
		bitDepth = decoder.hasAlpha() ? 32 : 24;
		decoder.decode(scratch = BufferUtils.createByteBuffer(texWidth * texHeight * perPixel), texWidth * perPixel,
				perPixel == 4 ? PNGDecoder.RGBA : PNGDecoder.RGB);
		if(height < texHeight - 1) {
			final int topOffset = (texHeight - 1) * texWidth * perPixel;
			final int bottomOffset = (height - 1) * texWidth * perPixel;
			for(int x = 0; x < texWidth; ++x) for(int i = 0; i < perPixel; ++i) {
				scratch.put(topOffset + x + i, scratch.get(x + i));
				scratch.put(bottomOffset + texWidth * perPixel + x + i, scratch.get(bottomOffset + x + i));
			}
		}
		if(width < texWidth - 1) for(int y = 0; y < texHeight; ++y) for(int j = 0; j < perPixel; ++j) {
			scratch.put((y + 1) * texWidth * perPixel - perPixel + j, scratch.get(y * texWidth * perPixel + j));
			scratch.put(y * texWidth * perPixel + width * perPixel + j,
					scratch.get(y * texWidth * perPixel + (width - 1) * perPixel + j));
		}
		if(!decoder.hasAlpha() && forceAlpha) {
			final ByteBuffer temp = BufferUtils.createByteBuffer(texWidth * texHeight * 4);
			for(int x2 = 0; x2 < texWidth; ++x2) for(int y2 = 0; y2 < texHeight; ++y2) {
				final int srcOffset = y2 * 3 + x2 * texHeight * 3;
				final int dstOffset = y2 * 4 + x2 * texHeight * 4;
				temp.put(dstOffset, scratch.get(srcOffset));
				temp.put(dstOffset + 1, scratch.get(srcOffset + 1));
				temp.put(dstOffset + 2, scratch.get(srcOffset + 2));
				if(x2 < getHeight() && y2 < getWidth()) temp.put(dstOffset + 3, (byte) -1);
				else temp.put(dstOffset + 3, (byte) 0);
			}
			bitDepth = 32;
			scratch = temp;
		}
		scratch.position(0);
		return scratch;
	}

	@Override
	public ByteBuffer loadImage(final InputStream fis, final boolean flipped, final int[] transparent)
			throws IOException {
		return this.loadImage(fis, flipped, false, transparent);
	}
}
