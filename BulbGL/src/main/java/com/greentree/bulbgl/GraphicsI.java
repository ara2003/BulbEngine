package com.greentree.bulbgl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Arseny Latyshev
 */
public interface GraphicsI  {

	int GL_ALWAYS = 519;
	int GL_BGRA = 32993;
	int GL_BLEND = 3042;
	int GL_CLAMP = 10496;
	int GL_CLIP_PLANE0 = 12288;
	int GL_CLIP_PLANE1 = 12289;
	int GL_CLIP_PLANE2 = 12290;
	int GL_CLIP_PLANE3 = 12291;
	int GL_COLOR_BUFFER_BIT = 16384;
	int GL_COLOR_CLEAR_VALUE = 3106;
	int GL_COLOR_SUM_EXT = 33880;
	int GL_COMPILE = 4864;
	int GL_COMPILE_AND_EXECUTE = 4865;
	int GL_DEPTH_BUFFER_BIT = 256;
	int GL_DEPTH_TEST = 2929;
	int GL_DST_ALPHA = 772;
	int GL_EQUAL = 514;
	int GL_LINE_SMOOTH = 2848;
	int GL_LINE_STRIP = 3;
	int GL_LINE_WIDTH = 2849;
	int GL_LINEAR = 9729;
	int GL_LINES = 1;
	int GL_MAX_TEXTURE_SIZE = 3379;
	int GL_MIRROR_CLAMP_TO_EDGE_EXT = 34627;
	int GL_MODELVIEW_MATRIX = 2982;
	int GL_MODULATE = 8448;
	int GL_NEAREST = 9728;
	int GL_NOTEQUAL = 517;
	int GL_ONE = 1;
	int GL_ONE_MINUS_DST_ALPHA = 773;
	int GL_ONE_MINUS_SRC_ALPHA = 771;
	int GL_ONE_MINUS_SRC_COLOR = 769;
	int GL_POINT_SMOOTH = 2832;
	int GL_POINTS = 0;
	int GL_POLYGON_SMOOTH = 2881;
	int GL_QUADS = 7;
	int GL_RGB = 6407;
	int GL_RGBA = 6408;
	int GL_RGBA16 = 32859;
	int GL_RGBA8 = 6408;
	int GL_SCISSOR_TEST = 3089;
	int GL_SRC_ALPHA = 770;
	int GL_SRC_COLOR = 768;
	int GL_TEXTURE_2D = 3553;
	int GL_TEXTURE_ENV = 8960;
	int GL_TEXTURE_ENV_MODE = 8704;
	int GL_TEXTURE_MAG_FILTER = 10240;
	int GL_TEXTURE_MIN_FILTER = 10241;
	int GL_TEXTURE_WRAP_S = 10242;
	int GL_TEXTURE_WRAP_T = 10243;
	int GL_TRIANGLE_FAN = 6;
	int GL_TRIANGLES = 4;
	int GL_UNSIGNED_BYTE = 5121;
	
	boolean canSecondaryColor();
	
	void enterOrtho(final int p0, final int p1);
	void flush();
	float[] getCurrentColor();
	void glBegin(final int p0);
	void glBindTexture(final int p0, final int p1);
	void glBlendFunc(final int p0, final int p1);
	void glCallList(final int p0);
	void glClear(final int p0);
	void glClearColor(final float p0, final float p1, final float p2, final float p3);
	void glClearDepth(final float p0);
	void glClipPlane(final int p0, final DoubleBuffer p1);
	void glColor4f(final float p0, final float p1, final float p2, final float p3);
	void glColorMask(final boolean p0, final boolean p1, final boolean p2, final boolean p3);
	void glCopyTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5,
		final int p6, final int p7);
	void glDeleteLists(final int p0, final int p1);
	void glDeleteTextures(final IntBuffer p0);
	void glDepthFunc(final int p0);
	void glDepthMask(final boolean p0);
	void glDisable(final int p0);
	void glEnable(final int p0);
	void glEnd();
	void glEndList();
	int glGenLists(final int p0);
	void glGenTextures(final IntBuffer p0);
	void glGetError();
	void glGetFloat(final int p0, FloatBuffer buffer);
	int glGetInteger(final int p0);
	void glGetTexImage(final int p0, final int p1, final int p2, final int p3, final ByteBuffer p4);
	void glLineWidth(final float p0);
	void glLoadIdentity();
	void glLoadMatrix(final FloatBuffer p0);
	void glNewList(final int p0, final int p1);
	void glPointSize(final float p0);
	void glPopMatrix();
	void glPushMatrix();
	void glReadPixels(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5,
		final ByteBuffer p6);
	void glRotatef(final float p0, final float p1, final float p2, final float p3);
	void glScalef(final float p0, final float p1, final float p2);
	void glScissor(final int p0, final int p1, final int p2, final int p3);
	void glSecondaryColor3ubEXT(final byte p0, final byte p1, final byte p2);
	void glTexCoord2f(final float p0, final float p1);
	void glTranslatef(final float p0, final float p1, final float p2);
	void glVertex2f(final float p0, final float p1);
	void glVertex3f(final float p0, final float p1, final float p2);
	void initDisplay(final int p0, final int p1);
	void setGlobalAlphaScale(final float p0);
	void translate(float w, float h);
	void unbindTexture();
	void bindColor(float r, float g, float b, float a);
	void glDrawTriangles(int amountVertices, DataType type);
	void glDrawArraysTriangles(int i, int j);
	
}
