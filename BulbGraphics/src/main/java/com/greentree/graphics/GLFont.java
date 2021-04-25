package com.greentree.graphics;

public interface GLFont {
	
	default void drawString(final float x, final float y, final String p2) {
		drawString(x, y, p2, 0, p2.length()-1);
	}
	void drawString(final float x, final float y, final String p2, final int p4, final int p5);
	int getHeight(final String p0);
	int getLineHeight();
	int getWidth(final String p0);
	
}
