//
// Decompiled by Procyon v0.5.36
//
package com.greentree.opengl;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class EmptyImageData implements ImageData {

	private final int height;
	private final int width;

	public EmptyImageData(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public int getDepth() {
		return 32;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public ByteBuffer getImageBufferData() {
		return BufferUtils.createByteBuffer(getTexWidth() * getTexHeight() * 4);
	}

	@Override
	public int getTexHeight() {
		return InternalTextureLoader.get2Fold(height);
	}

	@Override
	public int getTexWidth() {
		return InternalTextureLoader.get2Fold(width);
	}

	@Override
	public int getWidth() {
		return width;
	}
}
