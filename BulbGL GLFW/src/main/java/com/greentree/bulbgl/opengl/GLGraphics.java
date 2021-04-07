package com.greentree.bulbgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.EXTSecondaryColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.greentree.bulbgl.DataType;
import com.greentree.bulbgl.GPrimitive;
import com.greentree.bulbgl.GraphicsI;


/** @author Arseny Latyshev */
public class GLGraphics implements GraphicsI {
	
	protected float alphaScale;
	private final float[] current;
	protected int height;
	private int width;

	@Override
	public void glTexCoord2f(final float x, final float y) {
		GL11.glTexCoord2f(x, y);
	}
	
	public GLGraphics() {
		this.current    = new float[]{1.0f,1.0f,1.0f,1.0f};
		this.alphaScale = 1.0f;
	}
	
	@Override
	public boolean canSecondaryColor() {
		return true;
	}
	
	@Override
	public void enterOrtho(final int xsize, final int ysize) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0, this.width, this.height, 0.0, 1.0, -1.0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glTranslatef((this.width - xsize) / 2, (this.height - ysize) / 2, 0.0f);
	}
	
	@Override
	public void flush() {
	}
	
	@Override
	public float[] getCurrentColor() {
		return this.current;
	}
	
	@Override
	public void glBegin(final int geomType) {
		GL11.glBegin(geomType);
	}
	
	@Override
	public void glBindTexture(final int target, final int id) {
		GL11.glBindTexture(target, id);
	}
	
	@Override
	public void glBlendFunc(final int src, final int dest) {
		GL11.glBlendFunc(src, dest);
	}
	
	@Override
	public void glCallList(final int id) {
		GL11.glCallList(id);
	}
	
	@Override
	public void glClear(final int value) {
		GL11.glClear(value);
	}
	
	@Override
	public void glClearColor(final float red, final float green, final float blue, final float alpha) {
		GL11.glClearColor(red, green, blue, alpha);
	}
	
	@Override
	public void glClearDepth(final float value) {
		GL11.glClearDepth(value);
	}
	
	@Override
	public void glClipPlane(final int plane, final DoubleBuffer buffer) {
		GL11.glClipPlane(plane, buffer);
	}
	
	@Override
	public void glColor4f(final float r, final float g, final float b, float a) {
		a *= this.alphaScale;
		GL11.glColor4f(this.current[0] = r, this.current[1] = g, this.current[2] = b, this.current[3] = a);
	}
	
	@Override
	public void glColorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
		GL11.glColorMask(red, green, blue, alpha);
	}
	
	@Override
	public void glCopyTexImage2D(final int target, final int level, final int internalFormat, final int x, final int y,
		final int width, final int height, final int border) {
		GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
	}
	
	@Override
	public void glDeleteLists(final int list, final int count) {
		GL11.glDeleteLists(list, count);
	}
	
	@Override
	public void glDeleteTextures(final IntBuffer buffer) {
		GL11.glDeleteTextures(buffer);
	}
	
	@Override
	public void glDepthFunc(final int func) {
		GL11.glDepthFunc(func);
	}
	
	@Override
	public void glDepthMask(final boolean mask) {
		GL11.glDepthMask(mask);
	}
	
	@Override
	public void glDisable(final int item) {
		GL11.glDisable(item);
	}
	
	@Override
	public void glEnable(final int item) {
		GL11.glEnable(item);
	}
	
	@Override
	public void glEnd() {
		GL11.glEnd();
	}
	
	@Override
	public void glEndList() {
		GL11.glEndList();
	}
	
	@Override
	public int glGenLists(final int count) {
		return GL11.glGenLists(count);
	}

	@Override
	public void glGenTextures(final IntBuffer ids) {
		GL11.glGenTextures(ids);
	}

	
	@Override
	public void glGetError() {
		GL11.glGetError();
	}
	
	@Override
	public void glGetFloat(final int id, final FloatBuffer ret) {
		GL11.glGetFloatv(id, ret);
	}
	
	@Override
	public int glGetInteger(final int id) {
		return GL11.glGetInteger(id);
	}
	
	@Override
	public void glGetTexImage(final int target, final int level, final int format, final int type,
		final ByteBuffer pixels) {
		GL11.glGetTexImage(target, level, format, type, pixels);
	}
	
	@Override
	public void glLineWidth(final float width) {
		GL11.glLineWidth(width);
	}
	
	@Override
	public void glLoadIdentity() {
		GL11.glLoadIdentity();
	}
	
	@Override
	public void glLoadMatrix(final FloatBuffer buffer) {
		GL11.glLoadMatrixf(buffer);
	}
	
	@Override
	public void glNewList(final int id, final int option) {
		GL11.glNewList(id, option);
	}
	
	@Override
	public void glPointSize(final float size) {
		GL11.glPointSize(size);
	}
	
	@Override
	public void glPopMatrix() {
		GL11.glPopMatrix();
	}
	
	@Override
	public void glPushMatrix() {
		GL11.glPushMatrix();
	}
	
	@Override
	public void glReadPixels(final int x, final int y, final int width, final int height, final int format,
		final int type, final ByteBuffer pixels) {
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
	}
	
	@Override
	public void glRotatef(final float angle, final float x, final float y, final float z) {
		GL11.glRotatef(angle, x, y, z);
	}
	
	@Override
	public void glScalef(final float x, final float y, final float z) {
		GL11.glScalef(x, y, z);
	}
	
	@Override
	public void glScissor(final int x, final int y, final int width, final int height) {
		GL11.glScissor(x, y, width, height);
	}
	
	@Override
	public void glSecondaryColor3ubEXT(final byte b, final byte c, final byte d) {
		EXTSecondaryColor.glSecondaryColor3ubEXT(b, c, d);
	}
	
	@Override
	public void glTranslatef(final float x, final float y, final float z) {
		GL11.glTranslatef(x, y, z);
	}
	
	@Override
	public void glVertex2f(final float x, final float y) {
		GL11.glVertex2f(x, y);
	}
	
	@Override
	public void glVertex3f(final float x, final float y, final float z) {
		GL11.glVertex3f(x, y, z);
	}
	
	@Override
	public void initDisplay(final int width, final int height) {
		this.width  = width;
		this.height = height;
		//		GL11.glGetString(7939);
		//		GL11.glEnable(3553);
		//		GL11.glShadeModel(7425);
		//		GL11.glDisable(2929);
		//		GL11.glDisable(2896);
		//		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		//		GL11.glClearDepth(1.0);
		//		GL11.glEnable(3042);
		//		GL11.glBlendFunc(770, 771);
		//		GL11.glViewport(0, 0, width, height);
		//		GL11.glMatrixMode(5888);
	}
	
	@Override
	public void setGlobalAlphaScale(final float alphaScale) {
		this.alphaScale = alphaScale;
	}
	
	@Override
	public void translate(final float x, final float y) {
		GL11.glTranslatef(x, y, 0);
	}

	@Override
	public void unbindTexture() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	@Override
	public void bindColor(float r, float g, float b, float a) {
		GL11.glColor4f(r, g, b, a);
	}
	
	@Override
	public void glDrawElements(GPrimitive p, int amountVertices, DataType type) {
		GL30.nglDrawElements(GL30.GL_TRIANGLES, amountVertices, Decoder.glType(type), MemoryUtil.NULL );
	}

	@Override
	public void glDrawArrays(GPrimitive p, int i, int j) {
		GL11.glDrawArrays(Decoder.glPrimitive(p), i, j);
		
	}


}
