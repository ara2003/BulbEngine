//
// Decompiled by Procyon v0.5.36
//
package com.greentree.engine.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface LoadableImageData extends ImageData {
	
	void configureEdging(final boolean p0);
	ByteBuffer loadImage(final InputStream p0) throws IOException;
	ByteBuffer loadImage(final InputStream p0, final boolean p1, final boolean p2, final int[] p3) throws IOException;
	ByteBuffer loadImage(final InputStream p0, final boolean p1, final int[] p2) throws IOException;
}
