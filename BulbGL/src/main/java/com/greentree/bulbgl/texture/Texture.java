package com.greentree.bulbgl.texture;

public interface Texture {
	
	void bind();
	void setMinFilter(final Filtering p0);
	void setMagFilter(final Filtering p0);
	void setWrap(Wrapping repeat);

	public static enum PixelFormat {
		RGB, RGBA;
	}
	public static enum Wrapping {
		REPEAT, MIRRORED_REPEAT, CLAMP_TO_EDGE, CLAMP_TO_BORDER;
	}
	public static enum Filtering {
		NEAREST, LINEAR;
	}
}
