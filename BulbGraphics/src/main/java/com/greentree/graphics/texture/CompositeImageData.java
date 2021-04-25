//
// Decompiled by Procyon v0.5.36
//
package com.greentree.graphics.texture;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.greentree.common.logger.Log;

public class CompositeImageData implements LoadableImageData {
	
	private LoadableImageData picked;
	private final ArrayList<LoadableImageData> sources;
	
	public CompositeImageData() {
		sources = new ArrayList<>();
	}
	
	public void add(final LoadableImageData data) {
		sources.add(data);
	}
	
	private void checkPicked() {
		if(picked == null)
			throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
	}
	
	@Override
	public void configureEdging(final boolean edging) {
		for(final LoadableImageData element : sources) element.configureEdging(edging);
	}
	
	@Override
	public int getDepth() {
		checkPicked();
		return picked.getDepth();
	}
	
	@Override
	public int getHeight() {
		checkPicked();
		return picked.getHeight();
	}
	
	@Override
	public ByteBuffer getImageBufferData() {
		checkPicked();
		return picked.getImageBufferData();
	}
	
	@Override
	public int getTexHeight() {
		checkPicked();
		return picked.getTexHeight();
	}
	
	@Override
	public int getTexWidth() {
		checkPicked();
		return picked.getTexWidth();
	}
	
	@Override
	public int getWidth() {
		checkPicked();
		return picked.getWidth();
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis) throws IOException {
		return this.loadImage(fis, false, null);
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream is, final boolean flipped, final boolean forceAlpha,
			final int[] transparent) throws IOException {
		ByteBuffer buffer = null;
		final BufferedInputStream in = new BufferedInputStream(is, is.available());
		in.mark(is.available());
		int i = 0;
		while(i < sources.size()) {
			in.reset();
			try {
				final LoadableImageData data = sources.get(i);
				buffer = data.loadImage(in, flipped, forceAlpha, transparent);
				picked = data;
			}catch(final Exception e) {
				Log.warn(sources.get(i).getClass() + " failed to read the data", e);
				++i;
				continue;
			}
			break;
		}
		return buffer;
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis, final boolean flipped, final int[] transparent)
			throws IOException {
		return this.loadImage(fis, flipped, false, transparent);
	}
}
