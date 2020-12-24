package com.greentree.engine.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.greentree.engine.opengl.BufferedImageUtil;
import com.greentree.engine.opengl.Texture;
import com.greentree.opengl.rendener.Renderer;
import com.greentree.opengl.rendener.SGL;

public final class BasicFont implements Font {
	
	private static final SGL GL = Renderer.get();
	private final boolean antiAlias;
	private final Map<Character, IntObject> customChars;
	private final java.awt.Font font;
	private int fontHeight;
	private FontMetrics fontMetrics;
	private int fontSize;
	private Texture fontTexture;
	private final int textureHeight;
	private int textureWidth;
	
	public BasicFont(final java.awt.Font font, final boolean antiAlias) {
		this(font, antiAlias, null);
	}
	
	public BasicFont(final java.awt.Font font, final boolean antiAlias, final char[] additionalChars) {
		customChars = new HashMap<>(300);
		fontSize = 0;
		fontHeight = 0;
		textureWidth = 512;
		textureHeight = 512;
		this.font = font;
		fontSize = font.getSize();
		this.antiAlias = antiAlias;
		createSet(additionalChars);
	}
	
	private void createSet(final char[] customCharsArray) {
		if((customCharsArray != null) && (customCharsArray.length > 0)) textureWidth *= 2;
		try {
			final BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, 2);
			final Graphics2D g = (Graphics2D) imgTemp.getGraphics();
			g.setColor(new Color(255, 255, 255, 1));
			g.fillRect(0, 0, textureWidth, textureHeight);
			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;
			for(int customCharsLength = customCharsArray != null ? customCharsArray.length : 0, i = 0; i < (256
					+ customCharsLength); ++i) {
				final char ch = i < 256 ? (char) i : customCharsArray[i - 256];
				BufferedImage fontImage = getFontImage(ch);
				final IntObject newIntObject = new IntObject();
				newIntObject.width = fontMetrics.charWidth(ch);
				newIntObject.height = fontMetrics.getHeight();
				if((positionX + newIntObject.width) >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
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
			fontTexture = BufferedImageUtil.getTexture(font.toString(), imgTemp);
		}catch(final IOException e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}
	
	private void drawQuad(final float drawX, final float drawY, final float drawX2, final float drawY2,
			final float srcX, final float srcY, final float srcX2, final float srcY2) {
		final float TextureSrcX = srcX / textureWidth;
		final float TextureSrcY = srcY / textureHeight;
		final float SrcWidth = srcX2 - srcX;
		final float SrcHeight = srcY2 - srcY;
		final float RenderWidth = SrcWidth / textureWidth;
		final float RenderHeight = SrcHeight / textureHeight;
		BasicFont.GL.glTexCoord2f(TextureSrcX, TextureSrcY);
		BasicFont.GL.glVertex2f(drawX, drawY);
		BasicFont.GL.glTexCoord2f(TextureSrcX, TextureSrcY + RenderHeight);
		BasicFont.GL.glVertex2f(drawX, drawY2);
		BasicFont.GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
		BasicFont.GL.glVertex2f(drawX2, drawY2);
		BasicFont.GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY);
		BasicFont.GL.glVertex2f(drawX2, drawY);
	}
	
	@Override
	public void drawString(final float x, final float y, final String whatchars) {
		this.drawString(x, y, whatchars, com.greentree.engine.gui.Color.white);
	}
	
	@Override
	public void drawString(final float x, final float y, final String whatchars,
			final com.greentree.engine.gui.Color color) {
		this.drawString(x, y, whatchars, color, 0, whatchars.length() - 1);
	}
	
	@Override
	public void drawString(final float x, final float y, final String whatchars,
			final com.greentree.engine.gui.Color color, final int startIndex, final int endIndex) {
		color.bind();
		fontTexture.bind();
		IntObject intObject = null;
		BasicFont.GL.glBegin(7);
		int totalwidth = 0;
		for(int i = 0; i < whatchars.length(); ++i) {
			final int charCurrent = whatchars.charAt(i);
			intObject = customChars.get((char) charCurrent);
			if(intObject != null) {
				if((i >= startIndex) || (i <= endIndex)) drawQuad(x + totalwidth, y, x + totalwidth + intObject.width,
						y + intObject.height, intObject.storedX, intObject.storedY, intObject.storedX + intObject.width,
						intObject.storedY + intObject.height);
				totalwidth += intObject.width;
			}
		}
		BasicFont.GL.glEnd();
	}
	
	private BufferedImage getFontImage(final char ch) {
		final BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
		final Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		if(antiAlias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch);
		if(charwidth <= 0) charwidth = 1;
		int charheight = fontMetrics.getHeight();
		if(charheight <= 0) charheight = fontSize;
		final BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
		final Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		if(antiAlias) gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gt.setFont(font);
		gt.setColor(Color.white);
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
		return fontHeight;
	}
	
	@Override
	public int getLineHeight() {
		return fontHeight;
	}
	
	@Override
	public int getWidth(final String whatchars) {
		int totalwidth = 0;
		IntObject intObject = null;
		int currentChar = 0;
		for(int i = 0; i < whatchars.length(); ++i) {
			currentChar = whatchars.charAt(i);
			intObject = customChars.get((char) currentChar);
			if(intObject != null) totalwidth += intObject.width;
		}
		return totalwidth;
	}
	
	private class IntObject {
		
		public int height;
		public int storedX;
		public int storedY;
		public int width;
	}
}
