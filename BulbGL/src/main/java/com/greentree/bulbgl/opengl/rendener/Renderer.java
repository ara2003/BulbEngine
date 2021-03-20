package com.greentree.bulbgl.opengl.rendener;

public class Renderer {
	
	public static final int DEFAULT_LINE_STRIP_RENDERER = 3;
	public static final int IMMEDIATE_RENDERER = 1;
	private static LineStripRenderer lineStripRenderer;
	public static final int QUAD_BASED_LINE_STRIP_RENDERER = 4;
	private static SGL renderer;
	public static final int VERTEX_ARRAY_RENDERER = 2;
	static {
		Renderer.renderer = new ImmediateModeOGLRenderer();
		Renderer.lineStripRenderer = new DefaultLineStripRenderer();
	}
	
	private Renderer() {
	}
	
	public static SGL get() {
		return Renderer.renderer;
	}
	
	public static LineStripRenderer getLineStripRenderer() {
		return Renderer.lineStripRenderer;
	}
	
	public static void setLineStripRenderer(final LineStripRenderer renderer) {
		Renderer.lineStripRenderer = renderer;
	}
	
	public static void setRenderer(final SGL r) {
		Renderer.renderer = r;
	}
}
