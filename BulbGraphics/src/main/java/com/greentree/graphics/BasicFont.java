package com.greentree.graphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.greentree.graphics.core.BufferedImageUtil;
import com.greentree.graphics.texture.GLTexture2D;

public final class BasicFont implements GLFont {

	private final boolean antiAlias;
	private final Map<Character, IntObject> customChars;
	private final java.awt.Font font;
	private int fontHeight;
	private FontMetrics fontMetrics;
	private int fontSize;
	private GLTexture2D fontTexture;
	private final int textureHeight;
	private int textureWidth;
	
	public BasicFont(final Font font, final boolean antiAlias) {
		customChars   = new HashMap<>(300);
		fontSize      = 0;
		fontHeight    = 0;
		textureWidth  = 512;
		textureHeight = 512;
		this.font          = font;
		fontSize      = font.getSize();
		this.antiAlias     = antiAlias;
		createSet();
	}

	private void createSet() {
		try {
			final BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, 2);
			final Graphics2D    g       = (Graphics2D) imgTemp.getGraphics();
			g.setColor(new java.awt.Color(255, 255, 255, 1));
			g.fillRect(0, 0, textureWidth, textureHeight);
			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;
			for(int i = 0; i < 256 + 1920; ++i) {
				final char      ch           = (char) i;
				BufferedImage   fontImage    = getFontImage(ch);
				final IntObject newIntObject = new IntObject();
				newIntObject.width  = fontMetrics.charWidth(ch);
				newIntObject.height = fontMetrics.getHeight();
				if(positionX + newIntObject.width >= textureWidth) {
					positionX  = 0;
					positionY += rowHeight;
					rowHeight  = 0;
				}
				newIntObject.storedX = positionX;
				newIntObject.storedY = positionY;
				if(newIntObject.height > fontHeight) fontHeight = newIntObject.height;
				if(newIntObject.height > rowHeight) rowHeight = newIntObject.height;
				g.drawImage(fontImage, positionX, positionY, null);
				positionX += newIntObject.width;
				customChars.put(ch, newIntObject);
				fontImage = null;
			}
			fontTexture = BufferedImageUtil.getTexture(imgTemp);
		}catch(final IOException e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}

	private void drawQuad(final float drawX, final float drawY, final float drawX2, final float drawY2,
			final float srcX, final float srcY, final float srcX2, final float srcY2) {
		final float TextureSrcX  = srcX / textureWidth;
		final float TextureSrcY  = srcY / textureHeight;
		final float SrcWidth     = srcX2 - srcX;
		final float SrcHeight    = srcY2 - srcY;
		final float RenderWidth  = SrcWidth / textureWidth;
		final float RenderHeight = SrcHeight / textureHeight;
		final float w = 0.25f / textureWidth, h = 0.25f / textureHeight;
		GL11.glTexCoord2f(TextureSrcX + RenderWidth - w, TextureSrcY + h);
		GL11.glVertex2f(drawX2, drawY2);
		GL11.glTexCoord2f(TextureSrcX + RenderWidth - w, TextureSrcY + RenderHeight - h);
		GL11.glVertex2f(drawX2, drawY);
		GL11.glTexCoord2f(TextureSrcX + w, TextureSrcY + RenderHeight - h);
		GL11.glVertex2f(drawX, drawY);
		GL11.glTexCoord2f(TextureSrcX + w, TextureSrcY + h);
		GL11.glVertex2f(drawX, drawY2);
	}

	@Override
	public void drawString(final float x, final float y, final String whatchars,
			final int startIndex, final int endIndex) {
		fontTexture.bind();
		IntObject intObject = null;
		GL11.glBegin(GL30.GL_QUADS);
		int totalwidth = 0;
		for(int i = 0; i < whatchars.length(); ++i) {
			final char charCurrent = whatchars.charAt(i);
			intObject = customChars.get(charCurrent);
			if(intObject != null) {
				if(i >= startIndex || i <= endIndex) drawQuad(x + totalwidth, y, x + totalwidth + intObject.width,
						y + intObject.height, intObject.storedX, intObject.storedY, intObject.storedX + intObject.width,
						intObject.storedY + intObject.height);
				totalwidth += intObject.width;
			}
		}
		GL11.glEnd();
	}

	private BufferedImage getFontImage(final char ch) {
		final BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
		final Graphics2D    g             = (Graphics2D) tempfontImage.getGraphics();
		if(antiAlias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch);
		if(charwidth <= 0) charwidth = 1;
		int charheight = fontMetrics.getHeight();
		if(charheight <= 0) charheight = fontSize;
		final BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
		final Graphics2D    gt        = (Graphics2D) fontImage.getGraphics();
		if(antiAlias) gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gt.setFont(font);
		gt.setColor(java.awt.Color.white);
		final int charx = 0;
		final int chary = 0;
		gt.drawString(String.valueOf(ch), charx, chary + fontMetrics.getAscent());
		return fontImage;
	}

	public int getHeight() {
		return fontHeight;
	}

	@Override
	public int getHeight(final String HeightString) {
		return getHeight();
	}

	@Override
	public int getLineHeight() {
		return fontHeight;
	}

	@Override
	public int getWidth(final String whatchars) {
		int       totalwidth  = 0;
		IntObject intObject   = null;
		char       currentChar = 0;
		for(int i = 0; i < whatchars.length(); ++i) {
			currentChar = whatchars.charAt(i);
			intObject   = customChars.get(currentChar);
			if(intObject != null) totalwidth += intObject.width;
		}
		return totalwidth;
	}

	private static class IntObject {

		public int height;
		public int storedX;
		public int storedY;
		public int width;
	}
}
