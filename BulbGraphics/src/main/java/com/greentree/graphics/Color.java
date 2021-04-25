package com.greentree.graphics;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class Color implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Color blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	public static final Color cyan = new Color(0.0f, 1.0f, 1.0f, 1.0f);
	public static final Color darkGray = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color lightGray = new Color(0.7f, 0.7f, 0.7f, 1.0f);
	public static final Color magenta = new Color(255, 0, 255, 255);
	public static final Color orange = new Color(255, 200, 0, 255);
	public static final Color pink = new Color(255, 175, 175, 255);
	public static final Color red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Color transparent = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Color white = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color yellow = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	public final float r, g, b, a;
	
	public Color(final Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}
	
	public Color(final float r, final float g, final float b) {
		this.a = 1.0f;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Color(final float r, final float g, final float b, final float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(final float[] buffer) {
		this.r = buffer[0];
		this.g = buffer[1];
		this.b = buffer[2];
		this.a = buffer[3];
	}
	
	public Color(final FloatBuffer buffer) {
		this.r = buffer.get();
		this.g = buffer.get();
		this.b = buffer.get();
		this.a = buffer.get();
	}
	
	public Color(final int value) {
		final int r = (value & 0xFF0000) >> 16;
		final int g = (value & 0xFF00) >> 8;
		final int b = value & 0xFF;
		int       a = (value & 0xFF000000) >> 24;
		if(a < 0) a += 256;
		if(a == 0) a = 255;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}
	
	public Color(final int r, final int g, final int b) {
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = 1.0f;
	}
	
	public Color(final int r, final int g, final int b, final int a) {
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}
	
	public static Color decode(final String nm) {
		return new Color(Integer.decode(nm));
	}
	
	public static Color getRandom() {
		switch(new Random().nextInt(14)) {
			case 0:
				return Color.black;
			case 1:
				return Color.blue;
			case 2:
				return Color.cyan;
			case 3:
				return Color.darkGray;
			case 6:
				return Color.green;
			case 7:
				return Color.lightGray;
			case 8:
				return Color.magenta;
			case 9:
				return Color.orange;
			case 10:
				return Color.pink;
			case 11:
				return Color.red;
			case 12:
				return Color.white;
			case 13:
				return Color.yellow;
			default:
				return Color.getRandom();
		}
	}
	
	public static Color getRandom(final int i, final int j) {
		final Random rand = new Random();
		return new Color(rand.nextInt(j - i) + i, rand.nextInt(j - i) + i, rand.nextInt(j - i) + i);
	}
//	TODO
//	public void add(final Color c) {
//		this.r += c.r;
//		this.g += c.g;
//		this.b += c.b;
//		this.a += c.a;
//	}
	
	public Color addToCopy(final Color c) {
		return new Color(this.r + c.r, this.g + c.g, this.b + c.g, this.a + c.r);
	}
	
	public void bind() {
		GL11.glColor4f(this.r, this.g, this.b, this.a);
	}
	
	public Color brighter() {
		return this.brighter(0.2f);
	}
	
	public Color brighter(float scale) {
		++scale;
		final Color temp = new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
		return temp;
	}
	
	public Color darker() {
		return this.darker(0.5f);
	}
	
	public Color darker(float scale) {
		scale = 1.0f - scale;
		return new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
	}
	
	@Override
	public boolean equals(final Object other) {
		if(other instanceof Color) {
			final Color o = (Color) other;
			return o.r == this.r && o.g == this.g && o.b == this.b && o.a == this.a;
		}
		return false;
	}
	
	public int getAlpha() {
		return (int) (this.a * 255.0f);
	}
	
	public int getAlphaByte() {
		return (int) (this.a * 255.0f);
	}
	
	public int getBlue() {
		return (int) (this.b * 255.0f);
	}
	
	public int getBlueByte() {
		return (int) (this.b * 255.0f);
	}
	
	public int getGreen() {
		return (int) (this.g * 255.0f);
	}
	
	public int getGreenByte() {
		return (int) (this.g * 255.0f);
	}
	
	public int getRed() {
		return (int) (this.r * 255.0f);
	}
	
	public int getRedByte() {
		return (int) (this.r * 255.0f);
	}
	
	@Override
	public int hashCode() {
		return (int) (this.r + this.g + this.b + this.a) * 255;
	}
	
	public Color multiply(final Color c) {
		return new Color(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
	}
	
//	TODO
//	public void scale(final float value) {
//		this.r *= value;
//		this.g *= value;
//		this.b *= value;
//		this.a *= value;
//	}
	
	public Color scaleCopy(final float value) {
		return new Color(this.r * value, this.g * value, this.b * value, this.a * value);
	}
	
	@Override
	public String toString() {
		return String.format("Color(%d,%d,%d,%d)", r, g, b, a);
	}

}
