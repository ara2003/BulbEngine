package com.greentree.engine.component;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.bulbgl.opengl.rendener.SGL;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;

/** @author Arseny Latyshev */
public class RectangleRenderer extends AbstractRendenerComponent {
	
	private static final long serialVersionUID = 1L;
	private final static SGL GL = Renderer.get();
	@DefoultValue("black")
	@EditorData()
	private Color color;
	@EditorData
	private float width, height;
	private transient Transform position;
	
	@Override
	public void render() {
		this.color.bind();
		RectangleRenderer.GL.glTranslatef(this.position.x(), this.position.y(), 0);
		RectangleRenderer.GL.glBegin(SGL.GL_QUADS);
		final float w = .5f / this.width, h = .5f / this.height;
		RectangleRenderer.GL.glTexCoord2f(w, h);
		RectangleRenderer.GL.glVertex3f(-this.width / 2, -this.height / 2, 0);
		RectangleRenderer.GL.glVertex3f(-this.width / 2, this.height / 2, 0);
		RectangleRenderer.GL.glVertex3f(this.width / 2, this.height / 2, 0);
		RectangleRenderer.GL.glVertex3f(this.width / 2, -this.height / 2, 0);
		RectangleRenderer.GL.glEnd();
		RectangleRenderer.GL.glTranslatef(-this.position.x(), -this.position.y(), 0);
	}
}
