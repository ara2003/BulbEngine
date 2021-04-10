package com.greentree.bulbgl.opengl;

import org.lwjgl.opengl.GL11;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.GraphicsI;

public class DefaultLineStripRenderer implements LineStripRenderer {
	
	private final GraphicsI GL;
	
	public DefaultLineStripRenderer() {
		GL = BulbGL.getGraphics();
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
		GL.glBegin(GL11.GL_LINE_STRIP);
	}
	
	@Override
	public void vertex(final float x, final float y) {
		GL.glVertex2f(x, y);
	}
}
