//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.opengl;

import java.nio.ByteBuffer;

public interface ImageData {
	
	int getDepth();
	int getHeight();
	ByteBuffer getImageBufferData();
	int getTexHeight();
	int getTexWidth();
	int getWidth();
}
