package com.greentree.engine.gui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.BufferUtils;

import com.greentree.engine.opengl.ImageData;

public class ImageBuffer implements ImageData {
	
	private final int height;
	private final byte[] rawData;
	private final int texHeight;
	private final int texWidth;
	private final int width;
	
	public ImageBuffer(final int width, final int height) {
		this.width = width;
		this.height = height;
		texWidth = get2Fold(width);
		texHeight = get2Fold(height);
		rawData = new byte[texWidth * texHeight * 4];
	}
	
	private int get2Fold(final int fold) {
		int ret;
		for(ret = 2; ret < fold; ret *= 2) {
		}
		return ret;
	}
	
	@Override
	public int getDepth() {
		return 32;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	public Image getImage() {
		return new Image(this);
	}
	
	public Image getImage(final int filter) {
		return new Image(this, filter);
	}
	
	@Override
	public ByteBuffer getImageBufferData() {
		final ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
		scratch.put(rawData);
		scratch.flip();
		return scratch;
	}
	
	public byte[] getRGBA() {
		return rawData;
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
	
	public void setRGBA(final int x, final int y, final int r, final int g, final int b, final int a) {
		if((x < 0) || (x >= width) || (y < 0) || (y >= height))
			throw new RuntimeException("Specified location: " + x + "," + y + " outside of image");
		final int ofs = (x + (y * texWidth)) * 4;
		if(ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
			rawData[ofs] = (byte) b;
			rawData[ofs + 1] = (byte) g;
			rawData[ofs + 2] = (byte) r;
			rawData[ofs + 3] = (byte) a;
		}else {
			rawData[ofs] = (byte) r;
			rawData[ofs + 1] = (byte) g;
			rawData[ofs + 2] = (byte) b;
			rawData[ofs + 3] = (byte) a;
		}
	}
}
