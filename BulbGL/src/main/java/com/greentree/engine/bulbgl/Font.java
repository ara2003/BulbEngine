package com.greentree.engine.bulbgl;

public interface Font {
	
	void drawString(final float x, final float y, final String p2);
	void drawString(final float x, final float y, final String p2, final Color color);
	void drawString(final float x, final float y, final String p2, final Color color, final int p4, final int p5);
	int getHeight(final String p0);
	int getLineHeight();
	int getWidth(final String p0);
}
