//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.bulbgl;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.Random;

import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;

public class Color implements Serializable {
	
	public static final Color black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Color blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	public static final Color cyan = new Color(0.0f, 1.0f, 1.0f, 1.0f);
	public static final Color darkGray = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	private static final SGL GL = Renderer.get();
	public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color lightGray = new Color(0.7f, 0.7f, 0.7f, 1.0f);
	public static final Color magenta = new Color(255, 0, 255, 255);
	public static final Color orange = new Color(255, 200, 0, 255);
	public static final Color pink = new Color(255, 175, 175, 255);
	public static final Color red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	private static final long serialVersionUID = 1L;
	public static final Color transparent = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Color white = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color yellow = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	public float a;
	public float b;
	public float g;
	public float r;
	
	public Color(final Color color) {
		a = 1.0f;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
	}
	
	public Color(final float r, final float g, final float b) {
		a = 1.0f;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Color(final float r, final float g, final float b, final float a) {
		this.a = 1.0f;
		this.r = Math.min(r, 1.0f);
		this.g = Math.min(g, 1.0f);
		this.b = Math.min(b, 1.0f);
		this.a = Math.min(a, 1.0f);
	}
	
	public Color(final FloatBuffer buffer) {
		a = 1.0f;
		r = buffer.get();
		g = buffer.get();
		b = buffer.get();
		a = buffer.get();
	}
	
	public Color(final int value) {
		a = 1.0f;
		final int r = (value & 0xFF0000) >> 16;
		final int g = (value & 0xFF00) >> 8;
		final int b = value & 0xFF;
		int a = (value & 0xFF000000) >> 24;
		if(a < 0) a += 256;
		if(a == 0) a = 255;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}
	
	public Color(final int r, final int g, final int b) {
		a = 1.0f;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		a = 1.0f;
	}
	
	public Color(final int r, final int g, final int b, final int a) {
		this.a = 1.0f;
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
				return black;
			case 1:
				return blue;
			case 2:
				return cyan;
			case 3:
				return darkGray;
			case 4:
				return getRandom();
			case 6:
				return green;
			case 7:
				return lightGray;
			case 8:
				return magenta;
			case 9:
				return orange;
			case 10:
				return pink;
			case 11:
				return red;
			case 12:
				return white;
			case 13:
				return yellow;
		}
		return red;
	}
	
	public void add(final Color c) {
		r += c.r;
		g += c.g;
		b += c.b;
		a += c.a;
	}
	
	public Color addToCopy(final Color c) {
		final Color color;
		final Color copy = color = new Color(r, g, b, a);
		color.r += c.r;
		final Color color2 = copy;
		color2.g += c.g;
		final Color color3 = copy;
		color3.b += c.b;
		final Color color4 = copy;
		color4.a += c.a;
		return copy;
	}
	
	public void bind() {
		Color.GL.glColor4f(r, g, b, a);
	}
	
	public Color brighter() {
		return this.brighter(0.2f);
	}
	
	public Color brighter(float scale) {
		++scale;
		final Color temp = new Color(r * scale, g * scale, b * scale, a);
		return temp;
	}
	
	public Color darker() {
		return this.darker(0.5f);
	}
	
	public Color darker(float scale) {
		scale = 1.0f - scale;
		final Color temp = new Color(r * scale, g * scale, b * scale, a);
		return temp;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(other instanceof Color) {
			final Color o = (Color) other;
			return (o.r == r) && (o.g == g) && (o.b == b) && (o.a == a);
		}
		return false;
	}
	
	public int getAlpha() {
		return (int) (a * 255.0f);
	}
	
	public int getAlphaByte() {
		return (int) (a * 255.0f);
	}
	
	public int getBlue() {
		return (int) (b * 255.0f);
	}
	
	public int getBlueByte() {
		return (int) (b * 255.0f);
	}
	
	public int getGreen() {
		return (int) (g * 255.0f);
	}
	
	public int getGreenByte() {
		return (int) (g * 255.0f);
	}
	
	public int getRed() {
		return (int) (r * 255.0f);
	}
	
	public int getRedByte() {
		return (int) (r * 255.0f);
	}
	
	@Override
	public int hashCode() {
		return (int) (r + g + b + a) * 255;
	}
	
	public Color multiply(final Color c) {
		return new Color(r * c.r, g * c.g, b * c.b, a * c.a);
	}
	
	public void scale(final float value) {
		r *= value;
		g *= value;
		b *= value;
		a *= value;
	}
	
	public Color scaleCopy(final float value) {
		final Color color;
		final Color copy = color = new Color(r, g, b, a);
		color.r *= value;
		final Color color2 = copy;
		color2.g *= value;
		final Color color3 = copy;
		color3.b *= value;
		final Color color4 = copy;
		color4.a *= value;
		return copy;
	}
	
	@Override
	public String toString() {
		return "Color (" + r + "," + g + "," + b + "," + a + ")";
	}
}
