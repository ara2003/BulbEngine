//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.opengl;

public interface Texture {
	
	void bind();
	float getHeight();
	int getImageHeight();
	int getImageWidth();
	byte[] getTextureData();
	int getTextureHeight();
	int getTextureID();
	String getTextureRef();
	int getTextureWidth();
	float getWidth();
	boolean hasAlpha();
	void release();
	void setTextureFilter(final int p0);
}
