//
// Decompiled by Procyon v0.5.36
//
package com.greentree.opengl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class ImageIOImageData implements LoadableImageData {
	
	private static final ColorModel glAlphaColorModel;
	private static final ColorModel glColorModel;
	static {
		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8,8,8,8}, true, false, 3,
				0);
		glColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8,8,8,0}, false, false, 1, 0);
	}
	private int depth;
	private boolean edging;
	private int height;
	private int texHeight;
	private int texWidth;
	private int width;
	
	public ImageIOImageData() {
		edging = true;
	}
	
	@Override
	public void configureEdging(final boolean edging) {
		this.edging = edging;
	}
	
	private void copyArea(final BufferedImage image, final int x, final int y, final int width, final int height,
			final int dx, final int dy) {
		final Graphics2D g = (Graphics2D) image.getGraphics();
		g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
	}
	
	@Override
	public int getDepth() {
		return depth;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public ByteBuffer getImageBufferData() {
		throw new RuntimeException("ImageIOImageData doesn't store it's image.");
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
	
	public ByteBuffer imageToByteBuffer(final BufferedImage image, final boolean flipped, final boolean forceAlpha,
			final int[] transparent) {
		ByteBuffer imageBuffer = null;
		int texWidth = 2;
		int texHeight = 2;
		while(texWidth < image.getWidth()) texWidth *= 2;
		while(texHeight < image.getHeight()) texHeight *= 2;
		width = image.getWidth();
		height = image.getHeight();
		this.texHeight = texHeight;
		this.texWidth = texWidth;
		final boolean useAlpha = image.getColorModel().hasAlpha() || forceAlpha;
		BufferedImage texImage;
		if(useAlpha) {
			depth = 32;
			final WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(ImageIOImageData.glAlphaColorModel, raster, false, new Hashtable<>());
		}else {
			depth = 24;
			final WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(ImageIOImageData.glColorModel, raster, false, new Hashtable<>());
		}
		final Graphics2D g = (Graphics2D) texImage.getGraphics();
		if(useAlpha) {
			g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
			g.fillRect(0, 0, texWidth, texHeight);
		}
		if(flipped) {
			g.scale(1.0, -1.0);
			g.drawImage(image, 0, -height, null);
		}else g.drawImage(image, 0, 0, null);
		if(edging) {
			if(height < texHeight - 1) {
				copyArea(texImage, 0, 0, width, 1, 0, texHeight - 1);
				copyArea(texImage, 0, height - 1, width, 1, 0, 1);
			}
			if(width < texWidth - 1) {
				copyArea(texImage, 0, 0, 1, height, texWidth - 1, 0);
				copyArea(texImage, width - 1, 0, 1, height, 1, 0);
			}
		}
		final byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
		if(transparent != null) for(int i = 0; i < data.length; i += 4) {
			boolean match = true;
			for(int c = 0; c < 3; ++c) {
				final int value = data[i + c] < 0 ? 256 + data[i + c] : data[i + c];
				if(value != transparent[c]) match = false;
			}
			if(match) data[i + 3] = 0;
		}
		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();
		g.dispose();
		return imageBuffer;
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis) throws IOException {
		return this.loadImage(fis, true, null);
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis, final boolean flipped, boolean forceAlpha,
			final int[] transparent) throws IOException {
		if(transparent != null) forceAlpha = true;
		final BufferedImage bufferedImage = ImageIO.read(fis);
		return imageToByteBuffer(bufferedImage, flipped, forceAlpha, transparent);
	}
	
	@Override
	public ByteBuffer loadImage(final InputStream fis, final boolean flipped, final int[] transparent)
			throws IOException {
		return this.loadImage(fis, flipped, false, transparent);
	}
}
