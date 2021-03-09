package com.greentree.engine.bulbgl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.geom2d.Shape2D;
import com.greentree.engine.opengl.TextureImpl;
import com.greentree.engine.opengl.rendener.LineStripRenderer;
import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.math.FastTrig;

public final class Graphics {
	
	private static boolean antialias;
	private static Color currentColor = Color.white;
	private static int currentDrawingMode = Graphics.MODE_NORMAL;
	private static Font font = new BasicFont(new java.awt.Font("", 10, 14), false);
	private static SGL GL = Renderer.get();
	private static float lineWidth = 1.0f;
	private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
	public static final int MODE_ADD = 5;
	public static final int MODE_ALPHA_BLEND = 3;
	public static final int MODE_ALPHA_MAP = 2;
	public static final int MODE_COLOR_MULTIPLY = 4;
	public static final int MODE_NORMAL = 1;
	public static final int MODE_SCREEN = 6;
	private static boolean pushed;
	private static final ByteBuffer readBuffer = BufferUtils.createByteBuffer(4);
	private static int screenHeight;
	private static int screenWidth;
	private static final ArrayList<FloatBuffer> stack = new ArrayList<>();
	private static int stackIndex;
	private static float sx = 1.0f;
	private static float sy = 1.0f;
	
	private Graphics() {
	}
	
	private static void checkPush() {
		if(!Graphics.pushed) {
			Graphics.GL.glPushMatrix();
			Graphics.pushed = true;
		}
	}
	
	public static void clear() {
		Graphics.GL.glClear(SGL.GL_COLOR_BUFFER_BIT);
	}
	
	public static void clearAlphaMap() {
		Graphics.pushTransform();
		Graphics.GL.glLoadIdentity();
		final int originalMode = Graphics.currentDrawingMode;
		Graphics.setDrawMode(Graphics.MODE_ALPHA_MAP);
		Graphics.setColor(new Color(0, 0, 0, 0));
		Graphics.fillRect(0.0f, 0.0f, Graphics.screenWidth, Graphics.screenHeight);
		Graphics.setColor(Graphics.currentColor);
		Graphics.setDrawMode(originalMode);
		Graphics.popTransform();
	}
	
	public static void clearClip() {
		Graphics.GL.glDisable(SGL.GL_SCISSOR_TEST);
	}
	
	public static void clearWorldClip() {
		Graphics.GL.glDisable(SGL.GL_CLIP_PLANE0);
		Graphics.GL.glDisable(SGL.GL_CLIP_PLANE1);
		Graphics.GL.glDisable(SGL.GL_CLIP_PLANE2);
		Graphics.GL.glDisable(SGL.GL_CLIP_PLANE3);
	}
	
	public static void copyArea(final Image target, final int x, final int y) {
		final int format = target.getTexture().hasAlpha() ? 6408 : 6407;
		target.bind();
		Graphics.GL.glCopyTexImage2D(3553, 0, format, x, Graphics.screenHeight - (y + target.getHeight()),
				target.getTexture().getTextureWidth(), target.getTexture().getTextureHeight(), 0);
		target.ensureInverted();
	}
	
	public static void drawArc(final float x1, final float y1, final float width, final float height, final float start,
			final float end) {
		Graphics.drawArc(x1, y1, width, height, 50, start, end);
	}
	
	public static void drawArc(final float x1, final float y1, final float width, final float height,
			final int segments, final float start, float end) {
		TextureImpl.bindNone();
		Graphics.currentColor.bind();
		while(end < start) end += 360.0f;
		final float cx = x1;
		final float cy = y1;
		Graphics.LSR.start();
		for(float step = 360f / segments, a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + (Math.cos(Math.toRadians(ang) - (Math.PI / 2)) * width));
			final float y2 = (float) (cy + (Math.sin(Math.toRadians(ang) - (Math.PI / 2)) * height));
			Graphics.LSR.vertex(x2, y2);
		}
		Graphics.LSR.end();
	}
	
	public static void drawGradientLine(final float x1, final float y1, final Color Color1, final float x2,
			final float y2, final Color Color2) {
		TextureImpl.bindNone();
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
		TextureImpl.bindNone();
		Graphics.GL.glBegin(1);
		Graphics.GL.glColor4f(red1, green1, blue1, alpha1);
		Graphics.GL.glVertex2f(x1, y1);
		Graphics.GL.glColor4f(red2, green2, blue2, alpha2);
		Graphics.GL.glVertex2f(x2, y2);
		Graphics.GL.glEnd();
	}
	
	public static void drawImage(final Image image, final float x, final float y) {
		image.draw(x, y);
	}
	
	public static void drawLine(float x1, float y1, float x2, float y2) {
		float lineWidth = Graphics.lineWidth - 1.0f;
		if(Graphics.LSR.applyGLLineFixes()) {
			if(x1 == x2) {
				if(y1 > y2) {
					final float temp = y2;
					y2 = y1;
					y1 = temp;
				}
				final float step = 1.0f / Graphics.sy;
				lineWidth /= Graphics.sy;
				Graphics.fillRect(x1 - (lineWidth / 2.0f), y1 - (lineWidth / 2.0f), lineWidth + step,
						(y2 - y1) + lineWidth + step);
				return;
			}
			if(y1 == y2) {
				if(x1 > x2) {
					final float temp = x2;
					x2 = x1;
					x1 = temp;
				}
				final float step = 1.0f / Graphics.sx;
				lineWidth /= Graphics.sx;
				Graphics.fillRect(x1 - (lineWidth / 2.0f), y1 - (lineWidth / 2.0f), (x2 - x1) + lineWidth + step,
						lineWidth + step);
				return;
			}
		}
		Graphics.currentColor.bind();
		TextureImpl.bindNone();
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
	
	@SuppressWarnings("exports")
	public static void drawOval(final Point2D p, final float d, final float d2) {
		if(p == null) return;
		Graphics.drawOval(p.getX(), p.getY(), d, d2);
	}
	
	public static void drawRect(final float x1, final float y1, final float width, final float height) {
		Graphics.getLineWidth();
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
		Graphics.drawLine(x + cornerRadius, y, (x + width) - cornerRadius, y);
		Graphics.drawLine(x, y + cornerRadius, x, (y + height) - cornerRadius);
		Graphics.drawLine(x + width, y + cornerRadius, x + width, (y + height) - cornerRadius);
		Graphics.drawLine(x + cornerRadius, y + height, (x + width) - cornerRadius, y + height);
		final float d = cornerRadius * 2;
		Graphics.drawArc((x + width) - d, (y + height) - d, d, d, segs, 0.0f, 90.0f);
		Graphics.drawArc(x, (y + height) - d, d, d, segs, 90.0f, 180.0f);
		Graphics.drawArc((x + width) - d, y, d, d, segs, 270.0f, 360.0f);
		Graphics.drawArc(x, y, d, d, segs, 180.0f, 270.0f);
	}

	@SuppressWarnings("exports")
	public static void drawShape(final Shape2D s) {
		Graphics.currentColor.bind();
		TextureImpl.bindNone();
		final List<Point2D> point = s.getPoints();
		Graphics.LSR.start();
		for(final Point2D l : point) {
			Graphics.LSR.vertex(l.getX(), l.getY());
		}
		Graphics.LSR.vertex(point.get(0).getX(), point.get(0).getY());
		Graphics.LSR.end();
	}
	
	public static void drawString(final String str, final float x, final float y) {
		Graphics.font.drawString(x, y, str, Graphics.currentColor);
	}
	
	public static void fillArc(final float x1, final float y1, final float width, final float height, final float start,
			final float end) {
		Graphics.fillArc(x1, y1, width, height, 50, start, end);
	}
	
	public static void fillArc(final float x1, final float y1, final float width, final float height,
			final int segments, final float start, float end) {
		TextureImpl.bindNone();
		Graphics.currentColor.bind();
		while(end < start) end += 360.0f;
		final float cx = x1 + (width / 2.0f);
		final float cy = y1 + (height / 2.0f);
		Graphics.GL.glBegin(6);
		final int step = 360 / segments;
		Graphics.GL.glVertex2f(cx, cy);
		for(int a = (int) start; a < (int) (end + step); a += step) {
			float ang = a;
			if(ang > end) ang = end;
			final float x2 = (float) (cx + (FastTrig.cos(Math.toRadians(ang)) * width));
			final float y2 = (float) (cy + (FastTrig.sin(Math.toRadians(ang)) * height));
			Graphics.GL.glVertex2f(x2, y2);
		}
		Graphics.GL.glEnd();
		if(Graphics.antialias) {
			Graphics.GL.glBegin(6);
			Graphics.GL.glVertex2f(cx, cy);
			if(end != 360.0f) end -= 10.0f;
			for(int a = (int) start; a < (int) (end + step); a += step) {
				float ang = a;
				if(ang > end) ang = end;
				final float x2 = (float) (cx + (FastTrig.cos(Math.toRadians(ang + 10.0f)) * width));
				final float y2 = (float) (cy + (FastTrig.sin(Math.toRadians(ang + 10.0f)) * height));
				Graphics.GL.glVertex2f(x2, y2);
			}
			Graphics.GL.glEnd();
		}
	}
	
	public static void fillOval(final float x1, final float y1, final float width, final float height) {
		Graphics.fillOval(x1, y1, width, height, 50);
	}
	
	public static void fillOval(final float x1, final float y1, final float width, final float height,
			final int segments) {
		Graphics.fillArc(x1, y1, width, height, segments, 0.0f, 360.0f);
	}

	@SuppressWarnings("exports")
	public static void fillOval(final Point2D c, final float f, final float g) {
		Graphics.fillOval(c.getX(), c.getY(), f, g);
	}
	
	public static void fillRect(final float x1, final float y1, final float width, final float height) {
		TextureImpl.bindNone();
		Graphics.currentColor.bind();
		Graphics.GL.glBegin(7);
		Graphics.GL.glVertex2f(x1, y1);
		Graphics.GL.glVertex2f(x1 + width, y1);
		Graphics.GL.glVertex2f(x1 + width, y1 + height);
		Graphics.GL.glVertex2f(x1, y1 + height);
		Graphics.GL.glEnd();
	}
	
	public static void fillRect(final float x, final float y, final float width, final float height,
			final Image pattern, final float offX, final float offY) {
		final int cols = (int) Math.ceil(width / pattern.getWidth()) + 2;
		final int rows = (int) Math.ceil(height / pattern.getHeight()) + 2;
		for(int c = 0; c < cols; ++c) for(int r = 0; r < rows; ++r)
			pattern.draw(((c * pattern.getWidth()) + x) - offX, ((r * pattern.getHeight()) + y) - offY);
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
		Graphics.fillRect((x + width) - cornerRadius, y + cornerRadius, cornerRadius, height - d);
		Graphics.fillRect(x + cornerRadius, (y + height) - cornerRadius, width - d, cornerRadius);
		Graphics.fillRect(x + cornerRadius, y + cornerRadius, width - d, height - d);
		Graphics.fillArc((x + width) - d, (y + height) - d, d, d, segs, 0.0f, 90.0f);
		Graphics.fillArc(x, (y + height) - d, d, d, segs, 90.0f, 180.0f);
		Graphics.fillArc((x + width) - d, y, d, d, segs, 270.0f, 360.0f);
		Graphics.fillArc(x, y, d, d, segs, 180.0f, 270.0f);
	}
	
	public static void getArea(final int x, final int y, final int width, final int height, final ByteBuffer target) {
		if(target.capacity() < (width * height * 4))
			throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
		Graphics.GL.glReadPixels(x, Graphics.screenHeight - y - height, width, height, 6408, 5121, target);
	}
	
	public static Color getBackground() {
		final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		Graphics.GL.glGetFloat(3106, buffer);
		return new Color(buffer);
	}
	
	public static Color getColor() {
		return new Color(Graphics.currentColor);
	}
	
	public static Font getFont() {
		return Graphics.font;
	}
	
	public static int getHeight() {
		return Graphics.screenHeight;
	}
	
	public static float getLineWidth() {
		return Graphics.lineWidth;
	}
	
	public static Color getPixel(final int x, final int y) {
		Graphics.GL.glReadPixels(x, Graphics.screenHeight - y, 1, 1, SGL.GL_RGBA, SGL.GL_UNSIGNED_BYTE,
				Graphics.readBuffer);
		return new Color(Graphics.translate(Graphics.readBuffer.get(0)), Graphics.translate(Graphics.readBuffer.get(1)),
				Graphics.translate(Graphics.readBuffer.get(2)), Graphics.translate(Graphics.readBuffer.get(3)));
	}
	
	public static int getWidth() {
		return Graphics.screenWidth;
	}
	
	public static void init(final int width, final int height) {
		BufferUtils.createDoubleBuffer(4);
		Graphics.screenWidth = width;
		Graphics.screenHeight = height;
		Graphics.setBackground(Color.gray);
		Graphics.setDrawMode(Graphics.currentDrawingMode);
	}
	
	public static boolean isAntiAlias() {
		return Graphics.antialias;
	}
	
	public static void popTransform() {
		if(Graphics.stackIndex == 0) throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
		--Graphics.stackIndex;
		final FloatBuffer oldBuffer = Graphics.stack.get(Graphics.stackIndex);
		Graphics.GL.glLoadMatrix(oldBuffer);
		Graphics.sx = oldBuffer.get(16);
		Graphics.sy = oldBuffer.get(17);
	}
	
	public static void pushTransform() {
		FloatBuffer buffer;
		if(Graphics.stackIndex >= Graphics.stack.size()) {
			buffer = BufferUtils.createFloatBuffer(18);
			Graphics.stack.add(buffer);
		}else buffer = Graphics.stack.get(Graphics.stackIndex);
		Graphics.GL.glGetFloat(2982, buffer);
		buffer.put(16, Graphics.sx);
		buffer.put(17, Graphics.sy);
		++Graphics.stackIndex;
	}
	
	public static void resetLineWidth() {
		Renderer.getLineStripRenderer().setWidth(1.0f);
		Graphics.GL.glLineWidth(1.0f);
		Graphics.GL.glPointSize(1.0f);
	}
	
	public static void resetTransform() {
		Graphics.sx = 1.0f;
		Graphics.sy = 1.0f;
		if(Graphics.pushed) {
			Graphics.GL.glPopMatrix();
			Graphics.pushed = false;
		}
	}
	
	public static void rotate(final float rx, final float ry, final float ang) {
		Graphics.checkPush();
		Graphics.translate(rx, ry);
		Graphics.GL.glRotatef(ang, 0.0f, 0.0f, 1.0f);
		Graphics.translate(-rx, -ry);
	}
	
	public static void scale(final float sx, final float sy) {
		Graphics.sx *= sx;
		Graphics.sy *= sy;
		Graphics.checkPush();
		Graphics.GL.glScalef(sx, sy, 1.0f);
	}
	
	public static void setAntiAlias(final boolean anti) {
		Graphics.antialias = anti;
		Graphics.LSR.setAntiAlias(anti);
		if(anti) Graphics.GL.glEnable(2881);
		else Graphics.GL.glDisable(2881);
	}
	
	public static void setBackground(final Color color) {
		Graphics.GL.glClearColor(color.r, color.g, color.b, color.a);
	}
	
	public static void setColor(final Color color) {
		if(color == null) return;
		Graphics.currentColor = new Color(color);
		Graphics.currentColor.bind();
	}
	
	public static void setDrawMode(final int mode) {
		Graphics.currentDrawingMode = mode;
		if(Graphics.currentDrawingMode == Graphics.MODE_NORMAL) {
			Graphics.GL.glEnable(3042);
			Graphics.GL.glColorMask(true, true, true, true);
			Graphics.GL.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
		}
		if(Graphics.currentDrawingMode == Graphics.MODE_ALPHA_MAP) {
			Graphics.GL.glDisable(3042);
			Graphics.GL.glColorMask(false, false, false, true);
		}
		if(Graphics.currentDrawingMode == Graphics.MODE_ALPHA_BLEND) {
			Graphics.GL.glEnable(3042);
			Graphics.GL.glColorMask(true, true, true, false);
			Graphics.GL.glBlendFunc(SGL.GL_DST_ALPHA, SGL.GL_ONE_MINUS_DST_ALPHA);
		}
		if(Graphics.currentDrawingMode == Graphics.MODE_COLOR_MULTIPLY) {
			Graphics.GL.glEnable(3042);
			Graphics.GL.glColorMask(true, true, true, true);
			Graphics.GL.glBlendFunc(SGL.GL_ONE_MINUS_SRC_COLOR, SGL.GL_SRC_COLOR);
		}
		if(Graphics.currentDrawingMode == Graphics.MODE_ADD) {
			Graphics.GL.glEnable(3042);
			Graphics.GL.glColorMask(true, true, true, true);
			Graphics.GL.glBlendFunc(1, 1);
		}
		if(Graphics.currentDrawingMode == Graphics.MODE_SCREEN) {
			Graphics.GL.glEnable(3042);
			Graphics.GL.glColorMask(true, true, true, true);
			Graphics.GL.glBlendFunc(1, SGL.GL_ONE_MINUS_SRC_COLOR);
		}
	}
	
	public static void setFont(final Font font) {
		Graphics.font = font;
	}
	
	public static void setLineWidth(final float width) {
		Graphics.lineWidth = width;
		Graphics.LSR.setWidth(width);
		Graphics.GL.glPointSize(width);
	}
	
	private static int translate(final byte b) {
		if(b < 0) return 256 + b;
		return b;
	}
	
	public static void translate(final float x, final float y) {
		Graphics.checkPush();
		Graphics.GL.glTranslatef(x, y, 0.0f);
	}
}
