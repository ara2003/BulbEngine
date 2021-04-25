package com.greentree.graphics;

import org.lwjgl.opengl.GL11;

/** @author Arseny Latyshev */
public enum GLPrimitive{
	
	TRIANGLES(GL11.GL_TRIANGLES),
	LINES(GL11.GL_LINES),
	LINE_LOOP(GL11.GL_LINE_LOOP),
	LINE_STRIP(GL11.GL_LINE_STRIP),
	TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP),
	TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN),
	POINTS(GL11.GL_POINTS),
	QUADS(GL11.GL_QUADS),
	QUAD_STRIP(GL11.GL_QUAD_STRIP),
	POLYGON(GL11.GL_POLYGON);
	
	private final int glPrimitive;
	
	private GLPrimitive(int i) {
		glPrimitive = i;
	}
	
	public int glEnum(){
		return glPrimitive;
	}
	
}
