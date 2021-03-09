package com.greentree.engine.opengl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;

import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.loading.ResourceLoader;

public class InternalTextureLoader {
	
	protected static SGL GL = Renderer.get();
	private static final InternalTextureLoader loader = new InternalTextureLoader();
	private int dstPixelFormat = 6408;
	private boolean holdTextureData;
	private final HashMap<String, TextureImpl> texturesLinear = new HashMap<>();
	private final HashMap<String, TextureImpl> texturesNearest = new HashMap<>();
	
	public static IntBuffer createIntBuffer(final int size) {
		final ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());
		return temp.asIntBuffer();
	}
	
	public static int createTextureID() {
		final IntBuffer tmp = InternalTextureLoader.createIntBuffer(1);
		InternalTextureLoader.GL.glGenTextures(tmp);
		return tmp.get(0);
	}
	
	public static InternalTextureLoader get() {
		return InternalTextureLoader.loader;
	}
	
	public static int get2Fold(final int fold) {
		int ret = 2;
		while(ret < fold) ret *= 2;
		return ret;
	}
	
	public void clear() {
		texturesLinear.clear();
		texturesNearest.clear();
	}
	
	public void clear(final String name) {
		texturesLinear.remove(name);
		texturesNearest.remove(name);
	}
	
	public Texture createTexture(final int width, final int height) throws IOException {
		return createTexture(width, height, 9728);
	}
	
	public Texture createTexture(final int width, final int height, final int filter) throws IOException {
		final ImageData ds = new EmptyImageData(width, height);
		return getTexture(ds, filter);
	}
	
	public Texture getTexture(final File source, final boolean flipped, final int filter) throws IOException {
		final String resourceName = source.getAbsolutePath();
		final InputStream in = new FileInputStream(source);
		return getTexture(in, resourceName, flipped, filter, null);
	}
	
	public Texture getTexture(final File source, final boolean flipped, final int filter, final int[] transparent)
			throws IOException {
		final String resourceName = source.getAbsolutePath();
		final InputStream in = new FileInputStream(source);
		return getTexture(in, resourceName, flipped, filter, transparent);
	}
	
	public Texture getTexture(final ImageData dataSource, final int filter) throws IOException {
		final int target = 3553;
		final ByteBuffer textureBuffer = dataSource.getImageBufferData();
		final int textureID = InternalTextureLoader.createTextureID();
		final TextureImpl texture = new TextureImpl("generated:" + dataSource, target, textureID);
		final int minFilter = filter;
		final int magFilter = filter;
		InternalTextureLoader.GL.glBindTexture(target, textureID);
		final int width = dataSource.getWidth();
		final int height = dataSource.getHeight();
		final boolean hasAlpha = dataSource.getDepth() == 32;
		texture.setTextureWidth(dataSource.getTexWidth());
		texture.setTextureHeight(dataSource.getTexHeight());
		final int texWidth = texture.getTextureWidth();
		final int texHeight = texture.getTextureHeight();
		final int srcPixelFormat = hasAlpha ? 6408 : 6407;
		final int componentCount = hasAlpha ? 4 : 3;
		texture.setWidth(width);
		texture.setHeight(height);
		texture.setAlpha(hasAlpha);
		final int max = InternalTextureLoader.GL.glGetInteger(SGL.GL_MAX_TEXTURE_SIZE);
		if(texWidth > max || texHeight > max)
			throw new IOException("Attempt to allocate a texture to big for the current hardware");
		if(holdTextureData) texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
		InternalTextureLoader.GL.glTexParameteri(target, 10241, minFilter);
		InternalTextureLoader.GL.glTexParameteri(target, 10240, magFilter);
		InternalTextureLoader.GL.glTexImage2D(target, 0, dstPixelFormat, InternalTextureLoader.get2Fold(width),
				InternalTextureLoader.get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
		return texture;
	}
	
	public Texture getTexture(final InputStream in, final String resourceName, final boolean flipped, final int filter)
			throws IOException {
		return getTexture(in, resourceName, flipped, filter, null);
	}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public TextureImpl getTexture(final InputStream in, final String resourceName, final boolean flipped,
			final int filter, final int[] transparent) throws IOException {
		HashMap hash = filter == 9728 ? texturesNearest : texturesLinear;
		String resName = resourceName;
		if(transparent != null) resName += ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
		resName += ":" + flipped;
		if(holdTextureData) {
			final TextureImpl tex = (TextureImpl) hash.get(resName);
			if(tex != null) return tex;
		}else {
			final SoftReference ref = (SoftReference) hash.get(resName);
			if(ref != null) {
				final TextureImpl tex = (TextureImpl) ref.get();
				if(tex != null) return tex;
				hash.remove(resName);
			}
		}
		try {
			InternalTextureLoader.GL.glGetError();
		}catch(final NullPointerException e) {
			throw new RuntimeException(
					"Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
		}
		final TextureImpl tex = getTexture(in, resourceName, 3553, filter, filter, flipped, transparent);
		tex.setCacheName(resName);
		if(holdTextureData) hash.put(resName, tex);
		else hash.put(resName, new SoftReference<>(tex));
		return tex;
	}
	
	private TextureImpl getTexture(final InputStream in, final String resourceName, final int target,
			final int magFilter, final int minFilter, final boolean flipped, final int[] transparent)
			throws IOException {
		final LoadableImageData imageData = ImageDataFactory.getImageDataFor(resourceName);
		final ByteBuffer textureBuffer = imageData.loadImage(new BufferedInputStream(in), flipped, transparent);
		final int textureID = InternalTextureLoader.createTextureID();
		final TextureImpl texture = new TextureImpl(resourceName, target, textureID);
		InternalTextureLoader.GL.glBindTexture(target, textureID);
		final int width = imageData.getWidth();
		final int height = imageData.getHeight();
		final boolean hasAlpha = imageData.getDepth() == 32;
		texture.setTextureWidth(imageData.getTexWidth());
		texture.setTextureHeight(imageData.getTexHeight());
		final int texWidth = texture.getTextureWidth();
		final int texHeight = texture.getTextureHeight();
		final int max = InternalTextureLoader.GL.glGetInteger(SGL.GL_MAX_TEXTURE_SIZE);
		if(texWidth > max || texHeight > max)
			throw new IOException("Attempt to allocate a texture to big for the current hardware");
		final int srcPixelFormat = hasAlpha ? 6408 : 6407;
		final int componentCount = hasAlpha ? 4 : 3;
		texture.setWidth(width);
		texture.setHeight(height);
		texture.setAlpha(hasAlpha);
		if(holdTextureData) texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
		//InternalTextureLoader.GL;
		InternalTextureLoader.GL.glTexParameteri(target, 10241, minFilter);
		//GL;
		InternalTextureLoader.GL.glTexParameteri(target, 10240, magFilter);
		InternalTextureLoader.GL.glTexImage2D(target, 0, dstPixelFormat, InternalTextureLoader.get2Fold(width),
				InternalTextureLoader.get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
		return texture;
	}
	
	public Texture getTexture(final String resourceName, final boolean flipped, final int filter) throws IOException {
		final InputStream in = ResourceLoader.getResourceAsStream(resourceName);
		return getTexture(in, resourceName, flipped, filter, null);
	}
	
	public Texture getTexture(final String resourceName, final boolean flipped, final int filter,
			final int[] transparent) throws IOException {
		final InputStream in = ResourceLoader.getResourceAsStream(resourceName);
		return getTexture(in, resourceName, flipped, filter, transparent);
	}
	
	public void reload() {
		Iterator<TextureImpl> texs = texturesLinear.values().iterator();
		while(texs.hasNext()) texs.next().reload();
		texs = texturesNearest.values().iterator();
		while(texs.hasNext()) texs.next().reload();
	}
	
	public int reload(final TextureImpl texture, final int srcPixelFormat, final int componentCount,
			final int minFilter, final int magFilter, final ByteBuffer textureBuffer) {
		final int target = 3553;
		final int textureID = InternalTextureLoader.createTextureID();
		InternalTextureLoader.GL.glBindTexture(target, textureID);
		InternalTextureLoader.GL.glTexParameteri(target, 10241, minFilter);
		InternalTextureLoader.GL.glTexParameteri(target, 10240, magFilter);
		InternalTextureLoader.GL.glTexImage2D(target, 0, dstPixelFormat, texture.getTextureWidth(),
				texture.getTextureHeight(), 0, srcPixelFormat, 5121, textureBuffer);
		return textureID;
	}
	
	public void set16BitMode() {
		dstPixelFormat = 32859;
	}
	
	public void setHoldTextureData(final boolean holdTextureData) {
		this.holdTextureData = holdTextureData;
	}
}
