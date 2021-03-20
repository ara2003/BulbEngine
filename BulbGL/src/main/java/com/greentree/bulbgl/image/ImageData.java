//
// Decompiled by Procyon v0.5.36
//
package com.greentree.bulbgl.image;

import java.nio.ByteBuffer;

public interface ImageData {
	
	int getDepth();
	ByteBuffer getImageBufferData();
	int getTexHeight();
	int getTexWidth();
	int getWidth();
	int getHeight();
	
}
