package com.greentree.bulbgl.opengl;

public interface LineStripRenderer {
	
	boolean applyGLLineFixes();
	void color(final float p0, final float p1, final float p2, final float p3);
	void end();
	void setAntiAlias(final boolean p0);
	void setLineCaps(final boolean p0);
	void setWidth(final float p0);
	void start();
	void vertex(final float p0, final float p1);
	
}
