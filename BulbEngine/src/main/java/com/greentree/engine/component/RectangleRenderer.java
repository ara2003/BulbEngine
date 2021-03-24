package com.greentree.engine.component;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.bulbgl.opengl.rendener.SGL;

/**
 * @author Arseny Latyshev
 *
 */
public class RectangleRenderer extends AbstractRendenerComponent {
	private static final long serialVersionUID = 1L;
	@EditorData(def = "black")
	private Color color;
	@EditorData
	private float width, height;
	private final static SGL GL = Renderer.get();
	private transient Transform position;
	
	@Override
	public void render() {
		color.bind();
		GL.glTranslatef(position.x, position.y, 0);
		GL.glBegin(SGL.GL_QUADS);
		final float w = .5f / width, h = .5f / height;
		GL.glTexCoord2f(w, h);
		GL.glVertex3f(-width / 2, -height / 2, 0);
		GL.glVertex3f(-width / 2, height / 2, 0);
		GL.glVertex3f(width / 2, height / 2, 0);
		GL.glVertex3f(width / 2, -height / 2, 0);
		GL.glEnd();
		GL.glTranslatef(-position.x, -position.y, 0);
	}

}
