package com.greentree.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

/** @author Arseny Latyshev */
public abstract class Graphics {

	private static final GLFont font = new BasicFont(new java.awt.Font("", 10, 14), false);
	
	public static void clearColor(final float r, final float g, final float b) {
		GL11.glClearColor(r, g, b, 1);
	}
	
	public static enum ClientState {
		VERTEX_ARRAY(GL11.GL_VERTEX_ARRAY), COLOR_ARRAY(GL11.GL_COLOR_ARRAY), TEXTURE_COORD_ARRAY(GL11.GL_TEXTURE_COORD_ARRAY);

		private final int glEnum;
	
		private ClientState(int glEnum) {
			this.glEnum = glEnum;
		}

		protected int glEnum() {
			return glEnum;
		}
		
	}
	
	public static void drawArc(final float x1, final float y1, final float width, final float height, final float start,
		final float end) {
		Graphics.drawArc(x1, y1, width, height, 50, start, end);
	}
	
	public static void drawArc(final float x1, final float y1, final float width, final float height,
		final int segments, final float start, float end) {
		while(end < start) end += 360.0f;
		final float cx = x1;
		final float cy = y1;
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(float step = 360f / segments, a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + Math.cos(Math.toRadians(ang) - Math.PI / 2) * width);
			final float y2 = (float) (cy + Math.sin(Math.toRadians(ang) - Math.PI / 2) * height);
			GL11.glVertex2f(x2, y2);
		}
		GL11.glEnd();
	}
	
	public static void drawGradientLine(final float x1, final float y1, final Color Color1, final float x2,
		final float y2, final Color Color2) {
		GL11.glBegin(1);
		Color1.bind();
		GL11.glVertex2f(x1, y1);
		Color2.bind();
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}
	
	public static void drawGradientLine(final float x1, final float y1, final float red1, final float green1,
		final float blue1, final float alpha1, final float x2, final float y2, final float red2, final float green2,
		final float blue2, final float alpha2) {
		GL11.glBegin(1);
		GL11.glColor4f(red1, green1, blue1, alpha1);
		GL11.glVertex2f(x1, y1);
		GL11.glColor4f(red2, green2, blue2, alpha2);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}
	
	public static void drawLine(final float x1, final float y1, final float x2, final float y2) {
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}
	
	public static void drawOval(final float x1, final float y1, final float width, final float height) {
		Graphics.drawOval(x1, y1, width, height, 50);
	}
	
	public static void drawOval(final float x1, final float y1, final float width, final float height,
		final int segments) {
		Graphics.drawArc(x1, y1, width, height, segments, 0.0f, 360.0f);
	}
	
	public static void drawRect(final float x1, final float y1, final float width, final float height) {
		try(MemoryStack stack = MemoryStack.stackPush()){
			Graphics.glVertexPointer(2, GLType.FLOAT, stack.floats(x1, y1, x1 + width, y1, x1 + width, y1 + height, x1, y1 + height));
			
			Graphics.glEnableClientState(ClientState.VERTEX_ARRAY);

			Graphics.glDrawArrays(GLPrimitive.LINE_LOOP, 0, 4);
		
			Graphics.glDisableClientState(ClientState.VERTEX_ARRAY);
		}
	}
	
	public static void drawRoundRect(final float x, final float y, final float width, final float height,
		final int cornerRadius) {
		Graphics.drawRoundRect(x, y, width, height, cornerRadius, 50);
	}
	
	public static void drawRoundRect(final float x, final float y, final float width, final float height,
		int cornerRadius, final int segs) {
		if(cornerRadius < 0) throw new IllegalArgumentException("corner radius must be > 0");
		if(cornerRadius == 0) {
			Graphics.drawRect(x, y, width, height);
			return;
		}
		final int mr = (int) Math.min(width, height) / 2;
		if(cornerRadius > mr) cornerRadius = mr;
		Graphics.drawLine(x + cornerRadius, y, x + width - cornerRadius, y);
		Graphics.drawLine(x, y + cornerRadius, x, y + height - cornerRadius);
		Graphics.drawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius);
		Graphics.drawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height);
		final float d = cornerRadius * 2;
		Graphics.drawArc(x + width - d, y + height - d, d, d, segs, 0.0f, 90.0f);
		Graphics.drawArc(x, y + height - d, d, d, segs, 90.0f, 180.0f);
		Graphics.drawArc(x + width - d, y, d, d, segs, 270.0f, 360.0f);
		Graphics.drawArc(x, y, d, d, segs, 180.0f, 270.0f);
	}
	
	public static void drawVector(final float x, final float y, final float vx, final float vy) {
		Graphics.drawLine(x, y, x + vx, y + vy);
	}

	public static void enableBlead() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void enableCullFace() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void enableDepthTest() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public static void disableBlead() {
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void disableCullFace() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public static void disableDepthTest() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	public static void fillArc(final float x1, final float y1, final float width, final float height, final float start,
		final float end) {
		Graphics.fillArc(x1, y1, width, height, 50, start, end);
	}
	
	public static void fillArc(final float x1, final float y1, final float width, final float height,
		final int segments, final float start, float end) {
		while(end < start) end += 360.0f;
		final float cx = x1 + width / 2.0f;
		final float cy = y1 + height / 2.0f;
		Graphics.glBegin(GL11.GL_LINE_STRIP);
		final int step = 360 / segments;
		GL11.glVertex2f(cx, cy);
		for(int a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + Math.cos(Math.toRadians(ang)) * width);
			final float y2 = (float) (cy + Math.sin(Math.toRadians(ang)) * height);
			GL11.glVertex2f(x2, y2);
		}
		Graphics.glEnd();
	}
	
	public static void fillOval(final float x1, final float y1, final float width, final float height) {
		Graphics.fillOval(x1, y1, width, height, 50);
	}
	
	public static void fillOval(final float x1, final float y1, final float width, final float height,
		final int segments) {
		Graphics.fillArc(x1, y1, width, height, segments, 0.0f, 360.0f);
	}
	
	public static void fillRect(final double x1, final double y1, final double width, final double height) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(x1, y1 - height, 0);
		GL11.glVertex3d(x1 + width, y1 - height, 0);
		GL11.glVertex3d(x1 + width, y1, 0);
		GL11.glVertex3d(x1, y1, 0);
		GL11.glEnd();
	}
	
	public static void fillRoundRect(final float x, final float y, final float width, final float height,
		final int cornerRadius) {
		Graphics.fillRoundRect(x, y, width, height, cornerRadius, 50);
	}
	
	public static void fillRoundRect(final float x, final float y, final float width, final float height,
		int cornerRadius, final int segs) {
		if(cornerRadius < 0) throw new IllegalArgumentException("corner radius must be > 0");
		if(cornerRadius == 0) {
			Graphics.fillRect(x, y, width, height);
			return;
		}
		final int mr = (int) Math.min(width, height) / 2;
		if(cornerRadius > mr) cornerRadius = mr;
		final float d = cornerRadius * 2;
		Graphics.fillRect(x + cornerRadius, y, width - d, cornerRadius);
		Graphics.fillRect(x, y + cornerRadius, cornerRadius, height - d);
		Graphics.fillRect(x + width - cornerRadius, y + cornerRadius, cornerRadius, height - d);
		Graphics.fillRect(x + cornerRadius, y + height - cornerRadius, width - d, cornerRadius);
		Graphics.fillRect(x + cornerRadius, y + cornerRadius, width - d, height - d);
		Graphics.fillArc(x + width - d, y + height - d, d, d, segs, 0.0f, 90.0f);
		Graphics.fillArc(x, y + height - d, d, d, segs, 90.0f, 180.0f);
		Graphics.fillArc(x + width - d, y, d, d, segs, 270.0f, 360.0f);
		Graphics.fillArc(x, y, d, d, segs, 180.0f, 270.0f);
	}
	
	public static GLFont getFont() {
		return font;
	}
	
	public static void glBegin(final GLPrimitive type) {
		GL11.glBegin(type.glEnum());
	}
	
	static void glBegin(final int type) {
		GL11.glBegin(type);
	}
	
	static void glClear(final int i) {
		GL11.glClear(i);
	}
	
	public static void glClearAll() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_ACCUM_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}
	
	public static void glDrawElements(final GLPrimitive primitive, final int countIndecies, final GLType type) {
		GL11.glDrawElements(primitive.glEnum(), countIndecies, type.glEnum(), 0);
	}
	
	public static void glEnd() {
		GL11.glEnd();
	}
	
	public static void rotate(final float rx, final float ry, final float ang) {
		Graphics.translate(rx, ry);
		GL11.glRotatef(ang, 0.0f, 0.0f, 1.0f);
		Graphics.translate(-rx, -ry);
	}
	
	public static void scale(final float sx, final float sy) {
		GL11.glScalef(sx, sy, 1.0f);
	}
	
	public static void scale(final float x, final float y, final float z) {
		GL11.glScalef(x, y, z);
	}
	
	public static void setClearDepth(final double d) {
		GL11.glClearDepth(d);
	}
	
	public static void setColor(final double r, final double g, final double b, final double a) {
		GL11.glColor4d(r, g, b, a);
	}
	
	
	public static void translate(final float x, final float y) {
		GL11.glTranslatef(x, y, 0.0f);
	}
	
	public static void translate(final float x, final float y, final float z) {
		GL11.glTranslatef(x, y, z);
	}

	public static void glDrawElements(GLPrimitive type, IntBuffer identites) {
		GL11.glDrawElements(type.glEnum(), identites);
	}

	public static void glVertexPointer(int vertexSize, GLType f, FloatBuffer buffer) {
		glVertexPointer(vertexSize,  f, 0, buffer);
	}

	public static void glColorPointer(int vertexSize, GLType f,  FloatBuffer buffer) {
		glColorPointer(vertexSize,  f, 0, buffer);
	}

	public static void glTexCoordPointer(int vertexSize, GLType f, FloatBuffer buffer) {
		glTexCoordPointer(vertexSize,  f, 0, buffer);
	}

	public static void glVertexPointer(int vertexSize, GLType f, int stride, FloatBuffer buffer) {
		GL11.glVertexPointer(vertexSize,  f.glEnum(), stride, buffer);
	}

	public static void glColorPointer(int vertexSize, GLType f, int stride, FloatBuffer buffer) {
		GL11.glColorPointer(vertexSize,  f.glEnum(), stride, buffer);
	}

	public static void glTexCoordPointer(int vertexSize, GLType f, int stride, FloatBuffer buffer) {
		GL11.glTexCoordPointer(vertexSize,  f.glEnum(), stride, buffer);
	}

	public static void glEnableClientState(ClientState vertexArray) {
		GL11.glEnableClientState(vertexArray.glEnum());
	}

	public static void glDisableClientState(ClientState vertexArray) {
		GL11.glDisableClientState(vertexArray.glEnum());
	}
	public static void popMatrix() {
		GL11.glPopMatrix();
	}
	public static void pushMatrix() {
		GL11.glPushMatrix();
	}
	
	public static void glDrawArrays(GLPrimitive primitive, int start, int count) {
		GL11.glDrawArrays(primitive.glEnum(), start, count);
	}

	public static FloatBuffer getProjectionMatrix() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, buf);
			return buf;
		}
	}
	public static FloatBuffer getModelViewMatrix() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, buf);
			return buf;
		}
	}

	public static void widthLine(int i) {
		GL11.glLineWidth(i);
	}
	
}
