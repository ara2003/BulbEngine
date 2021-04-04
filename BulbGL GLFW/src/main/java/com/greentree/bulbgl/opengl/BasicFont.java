package com.greentree.bulbgl.opengl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.greentree.bulbgl.BulbFont;
import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.bulbgl.glfw.BufferedImageUtil;
import com.greentree.bulbgl.texture.Texture;

public final class BasicFont implements BulbFont {
	
	private static final GraphicsI GL = BulbGL.getGraphics();
	private final boolean antiAlias;
	private final Map<Character, IntObject> customChars;
	private final java.awt.Font font;
	private int fontHeight;
	private FontMetrics fontMetrics;
	private int fontSize;
	private Texture fontTexture;
	private final int textureHeight;
	private int textureWidth;
	
	public BasicFont(final Font font, final boolean antiAlias) {
		this(font, antiAlias, null);
	}
	
	public BasicFont(final Font font, final boolean antiAlias, final char[] additionalChars) {
		this.customChars   = new HashMap<>(300);
		this.fontSize      = 0;
		this.fontHeight    = 0;
		this.textureWidth  = 512;
		this.textureHeight = 512;
		this.font          = font;
		this.fontSize      = font.getSize();
		this.antiAlias     = antiAlias;
		this.createSet(additionalChars);
	}
	
	private void createSet(final char[] customCharsArray) {
		if(customCharsArray != null && customCharsArray.length > 0) this.textureWidth *= 2;
		try {
			final BufferedImage imgTemp = new BufferedImage(this.textureWidth, this.textureHeight, 2);
			final Graphics2D    g       = (Graphics2D) imgTemp.getGraphics();
			g.setColor(new Color(255, 255, 255, 1));
			g.fillRect(0, 0, this.textureWidth, this.textureHeight);
			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;
			for(int customCharsLength = customCharsArray != null ? customCharsArray.length : 0,
				i = 0; i < 256 + customCharsLength; ++i) {
				final char      ch           = i < 256 ? (char) i : customCharsArray[i - 256];
				BufferedImage   fontImage    = this.getFontImage(ch);
				final IntObject newIntObject = new IntObject();
				newIntObject.width  = this.fontMetrics.charWidth(ch);
				newIntObject.height = this.fontMetrics.getHeight();
				if(positionX + newIntObject.width >= this.textureWidth) {
					positionX  = 0;
					positionY += rowHeight;
					rowHeight  = 0;
				}
				newIntObject.storedX = positionX;
				newIntObject.storedY = positionY;
				if(newIntObject.height > this.fontHeight) this.fontHeight = newIntObject.height;
				if(newIntObject.height > rowHeight) rowHeight = newIntObject.height;
				g.drawImage(fontImage, positionX, positionY, null);
				positionX += newIntObject.width;
				this.customChars.put(ch, newIntObject);
				fontImage = null;
			}
			this.fontTexture = BufferedImageUtil.getTexture(imgTemp);
		}catch(final IOException e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}
	
	private void drawQuad(final float drawX, final float drawY, final float drawX2, final float drawY2,
		final float srcX, final float srcY, final float srcX2, final float srcY2) {
		final float TextureSrcX  = srcX / this.textureWidth;
		final float TextureSrcY  = srcY / this.textureHeight;
		final float SrcWidth     = srcX2 - srcX;
		final float SrcHeight    = srcY2 - srcY;
		final float RenderWidth  = SrcWidth / this.textureWidth;
		final float RenderHeight = SrcHeight / this.textureHeight;
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
		this.drawString(x, y, whatchars, com.greentree.bulbgl.Color.white);
	}
	
	@Override
	public void drawString(final float x, final float y, final String whatchars,
		final com.greentree.bulbgl.Color color) {
		this.drawString(x, y, whatchars, color, 0, whatchars.length() - 1);
	}
	
	@Override
	public void drawString(final float x, final float y, final String whatchars, final com.greentree.bulbgl.Color color,
		final int startIndex, final int endIndex) {
		color.bind();
		this.fontTexture.bind();
		IntObject intObject = null;
		BasicFont.GL.glBegin(7);
		int totalwidth = 0;
		for(int i = 0; i < whatchars.length(); ++i) {
			final int charCurrent = whatchars.charAt(i);
			intObject = this.customChars.get((char) charCurrent);
			if(intObject != null) {
				if(i >= startIndex || i <= endIndex) this.drawQuad(x + totalwidth, y, x + totalwidth + intObject.width,
					y + intObject.height, intObject.storedX, intObject.storedY, intObject.storedX + intObject.width,
					intObject.storedY + intObject.height);
				totalwidth += intObject.width;
			}
		}
		BasicFont.GL.glEnd();
	}
	
	private BufferedImage getFontImage(final char ch) {
		final BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
		final Graphics2D    g             = (Graphics2D) tempfontImage.getGraphics();
		if(this.antiAlias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(this.font);
		this.fontMetrics = g.getFontMetrics();
		int charwidth = this.fontMetrics.charWidth(ch);
		if(charwidth <= 0) charwidth = 1;
		int charheight = this.fontMetrics.getHeight();
		if(charheight <= 0) charheight = this.fontSize;
		final BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
		final Graphics2D    gt        = (Graphics2D) fontImage.getGraphics();
		if(this.antiAlias) gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gt.setFont(this.font);
		gt.setColor(Color.white);
		final int charx = 0;
		final int chary = 0;
		gt.drawString(String.valueOf(ch), charx, chary + this.fontMetrics.getAscent());
		return fontImage;
	}
	
	public int getHeight() {
		return this.fontHeight;
	}
	
	@Override
	public int getHeight(final String HeightString) {
		return this.fontHeight;
	}
	
	@Override
	public int getLineHeight() {
		return this.fontHeight;
	}
	
	@Override
	public int getWidth(final String whatchars) {
		int       totalwidth  = 0;
		IntObject intObject   = null;
		int       currentChar = 0;
		for(int i = 0; i < whatchars.length(); ++i) {
			currentChar = whatchars.charAt(i);
			intObject   = this.customChars.get((char) currentChar);
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
