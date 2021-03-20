package com.greentree.bulbgl.opengl.rendener;

public class DefaultLineStripRenderer implements LineStripRenderer {
	
	private final SGL GL;
	
	public DefaultLineStripRenderer() {
		GL = Renderer.get();
	}
	
	@Override
	public boolean applyGLLineFixes() {
		return true;
	}
	
	@Override
	public void color(final float r, final float g, final float b, final float a) {
		GL.glColor4f(r, g, b, a);
	}
	
	@Override
	public void end() {
		GL.glEnd();
	}
	
	@Override
	public void setAntiAlias(final boolean antialias) {
		if(antialias) GL.glEnable(2848);
		else GL.glDisable(2848);
	}
	
	@Override
	public void setLineCaps(final boolean caps) {
	}
	
	@Override
	public void setWidth(final float width) {
		GL.glLineWidth(width);
	}
	
	@Override
	public void start() {
		GL.glBegin(3);
	}
	
	@Override
	public void vertex(final float x, final float y) {
		GL.glVertex2f(x, y);
	}
}
