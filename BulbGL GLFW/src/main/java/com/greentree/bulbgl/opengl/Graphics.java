package com.greentree.bulbgl.opengl;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.greentree.bulbgl.BulbFont;
import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.GraphicsI;
import com.greentree.common.math.FastTrig;
import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.geom2d.Shape2D;

public final class Graphics {
	
	private static BulbFont font = new BasicFont(new java.awt.Font("", 10, 14), false);
	private static GraphicsI GL = BulbGL.getGraphics();
	private static LineStripRenderer LSR = new DefaultLineStripRenderer();
	
	private Graphics() {
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
		Graphics.GL.glBegin(GL11.GL_LINE_STRIP);
		for(float step = 360f / segments, a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + Math.cos(Math.toRadians(ang) - Math.PI / 2) * width);
			final float y2 = (float) (cy + Math.sin(Math.toRadians(ang) - Math.PI / 2) * height);
			Graphics.GL.glVertex2f(x2, y2);
		}
		Graphics.GL.glEnd();
	}
	
	public static void drawGradientLine(final float x1, final float y1, final Color Color1, final float x2,
		final float y2, final Color Color2) {
		Graphics.GL.glBegin(1);
		Color1.bind();
		Graphics.GL.glVertex2f(x1, y1);
		Color2.bind();
		Graphics.GL.glVertex2f(x2, y2);
		Graphics.GL.glEnd();
	}
	
	public static void drawGradientLine(final float x1, final float y1, final float red1, final float green1,
		final float blue1, final float alpha1, final float x2, final float y2, final float red2, final float green2,
		final float blue2, final float alpha2) {
		Graphics.GL.glBegin(1);
		Graphics.GL.glColor4f(red1, green1, blue1, alpha1);
		Graphics.GL.glVertex2f(x1, y1);
		Graphics.GL.glColor4f(red2, green2, blue2, alpha2);
		Graphics.GL.glVertex2f(x2, y2);
		Graphics.GL.glEnd();
	}
	
	public static void drawLine(final float x1, final float y1, final float x2, final float y2) {
		Graphics.LSR.start();
		Graphics.LSR.vertex(x1, y1);
		Graphics.LSR.vertex(x2, y2);
		Graphics.LSR.end();
	}
	
	public static void drawOval(final float x1, final float y1, final float width, final float height) {
		Graphics.drawOval(x1, y1, width, height, 50);
	}
	
	public static void drawOval(final float x1, final float y1, final float width, final float height,
		final int segments) {
		Graphics.drawArc(x1, y1, width, height, segments, 0.0f, 360.0f);
	}
	
	
	public static void drawOval(final Point2D p, final float d, final float d2) {
		if(p == null) return;
		Graphics.drawOval(p.getX(), p.getY(), d, d2);
	}
	
	public static void drawRect(final float x1, final float y1, final float width, final float height) {
		Graphics.drawLine(x1, y1, x1 + width, y1);
		Graphics.drawLine(x1 + width, y1, x1 + width, y1 + height);
		Graphics.drawLine(x1 + width, y1 + height, x1, y1 + height);
		Graphics.drawLine(x1, y1 + height, x1, y1);
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
	
	
	public static void drawShape(final Shape2D s) {
		final List<Point2D> point = s.getPoints();
		Graphics.LSR.start();
		for(final Point2D l : point) Graphics.LSR.vertex(l.getX(), l.getY());
		Graphics.LSR.vertex(point.get(0).getX(), point.get(0).getY());
		Graphics.LSR.end();
	}
	
	public static void drawString(final String str, final float x, final float y) {
		Graphics.font.drawString(x, y, str);
	}
	
	public static void drawVector(final float x, final float y, final float vx, final float vy) {
		Graphics.drawLine(x, y, x + vx, y + vy);
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
		Graphics.GL.glBegin(6);
		final int step = 360 / segments;
		Graphics.GL.glVertex2f(cx, cy);
		for(int a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + FastTrig.cos(Math.toRadians(ang)) * width);
			final float y2 = (float) (cy + FastTrig.sin(Math.toRadians(ang)) * height);
			Graphics.GL.glVertex2f(x2, y2);
		}
		Graphics.GL.glEnd();
	}
	
	public static void fillOval(final float x1, final float y1, final float width, final float height) {
		Graphics.fillOval(x1, y1, width, height, 50);
	}
	
	
	public static void fillOval(final float x1, final float y1, final float width, final float height,
		final int segments) {
		Graphics.fillArc(x1, y1, width, height, segments, 0.0f, 360.0f);
	}
	
	public static void fillOval(final Point2D c, final float f, final float g) {
		Graphics.fillOval(c.getX(), c.getY(), f, g);
	}
	
	public static void fillRect(final double x1, final double y1, final double width, final double height) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(x1, y1 + height, 0);
		GL11.glVertex3d(x1 + width, y1 + height, 0);
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
	
	public static Color getBackground() {
		final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		Graphics.GL.glGetFloat(3106, buffer);
		return new Color(buffer);
	}
	
	public static BulbFont getFont() {
		return Graphics.font;
	}
	
	public static void resetLineWidth() {
		Graphics.LSR.setWidth(1.0f);
		Graphics.GL.glLineWidth(1.0f);
		Graphics.GL.glPointSize(1.0f);
	}
	
	public static void rotate(final float rx, final float ry, final float ang) {
		Graphics.translate(rx, ry);
		Graphics.GL.glRotatef(ang, 0.0f, 0.0f, 1.0f);
		Graphics.translate(-rx, -ry);
	}
	
	public static void scale(final float sx, final float sy) {
		Graphics.GL.glScalef(sx, sy, 1.0f);
	}
	
	public static void setAntiAlias(final boolean anti) {
		Graphics.LSR.setAntiAlias(anti);
		if(anti) Graphics.GL.glEnable(2881);
		else Graphics.GL.glDisable(2881);
	}
	
	public static void setFont(final BulbFont font) {
		Graphics.font = font;
	}
	
	public static void setLineWidth(final float width) {
		Graphics.LSR.setWidth(width);
		Graphics.GL.glPointSize(width);
	}
	
	public static void translate(final float x, final float y) {
		Graphics.GL.glTranslatef(x, y, 0.0f);
	}
}
