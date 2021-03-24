package com.greentree.bulbgl.image;

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
