package com.greentree.engine.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.greentree.engine.Log;
import com.greentree.opengl.rendener.Renderer;
import com.greentree.opengl.rendener.SGL;

public class TextureImpl implements Texture {
	
	protected static SGL GL = Renderer.get();
	
	static Texture lastBind;
	private boolean alpha;
	
	private String cacheName;
	
	private int height;
	
	private float heightRatio;
	
	private String ref;
	private ReloadData reloadData;
	private int target;
	private int texHeight;
	private int textureID;
	private int texWidth;
	private int width;
	private float widthRatio;
	protected TextureImpl() {
	}
	public TextureImpl(final String ref, final int target, final int textureID) {
		this.target = target;
		this.ref = ref;
		this.textureID = textureID;
		TextureImpl.lastBind = this;
	}
	public static void bindNone() {
		TextureImpl.lastBind = null;
		TextureImpl.GL.glDisable(3553);
	}
	public static Texture getLastBind() {
		return TextureImpl.lastBind;
	}
	
	public static void unbind() {
		TextureImpl.lastBind = null;
	}
	
	@Override
	public void bind() {
		if(TextureImpl.lastBind != this) {
			TextureImpl.lastBind = this;
			TextureImpl.GL.glEnable(3553);
			TextureImpl.GL.glBindTexture(target, textureID);
		}
	}
	
	protected IntBuffer createIntBuffer(final int size) {
		final ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());
		return temp.asIntBuffer();
	}
	
	@Override
	public float getHeight() {
		return heightRatio;
	}
	
	@Override
	public int getImageHeight() {
		return height;
	}
	
	@Override
	public int getImageWidth() {
		return width;
	}
	
	@Override
	public byte[] getTextureData() {
		final ByteBuffer buffer = BufferUtils.createByteBuffer((hasAlpha() ? 4 : 3) * texWidth * texHeight);
		bind();
		TextureImpl.GL.glGetTexImage(3553, 0, hasAlpha() ? 6408 : 6407, 5121, buffer);
		final byte[] data = new byte[buffer.limit()];
		buffer.get(data);
		buffer.clear();
		return data;
	}
	
	@Override
	public int getTextureHeight() {
		return texHeight;
	}
	
	@Override
	public int getTextureID() {
		return textureID;
	}
	
	@Override
	public String getTextureRef() {
		return ref;
	}
	
	@Override
	public int getTextureWidth() {
		return texWidth;
	}
	
	@Override
	public float getWidth() {
		return widthRatio;
	}
	
	@Override
	public boolean hasAlpha() {
		return alpha;
	}
	
	@Override
	public void release() {
		final IntBuffer texBuf = createIntBuffer(1);
		texBuf.put(textureID);
		texBuf.flip();
		TextureImpl.GL.glDeleteTextures(texBuf);
		if(TextureImpl.lastBind == this) TextureImpl.bindNone();
		if(cacheName != null) InternalTextureLoader.get().clear(cacheName);
		else InternalTextureLoader.get().clear(ref);
	}
	
	public void reload() {
		if(reloadData != null) textureID = reloadData.reload();
	}
	
	public void setAlpha(final boolean alpha) {
		this.alpha = alpha;
	}
	
	public void setCacheName(final String cacheName) {
		this.cacheName = cacheName;
	}
	
	private void setHeight() {
		if(texHeight != 0) heightRatio = height / (float) texHeight;
	}
	
	public void setHeight(final int height) {
		this.height = height;
		this.setHeight();
	}
	
	public void setTextureData(final int srcPixelFormat, final int componentCount, final int minFilter,
			final int magFilter, final ByteBuffer textureBuffer) {
		(reloadData = new ReloadData()).srcPixelFormat = srcPixelFormat;
		reloadData.componentCount = componentCount;
		reloadData.minFilter = minFilter;
		reloadData.magFilter = magFilter;
		reloadData.textureBuffer = textureBuffer;
	}
	
	@Override
	public void setTextureFilter(final int textureFilter) {
		bind();
		TextureImpl.GL.glTexParameteri(target, 10241, textureFilter);
		TextureImpl.GL.glTexParameteri(target, 10240, textureFilter);
	}
	
	public void setTextureHeight(final int texHeight) {
		this.texHeight = texHeight;
		this.setHeight();
	}
	
	public void setTextureID(final int textureID) {
		this.textureID = textureID;
	}
	
	public void setTextureWidth(final int texWidth) {
		this.texWidth = texWidth;
		this.setWidth();
	}
	
	private void setWidth() {
		if(texWidth != 0) widthRatio = width / (float) texWidth;
	}
	
	public void setWidth(final int width) {
		this.width = width;
		this.setWidth();
	}
	
	private class ReloadData {
		
		private int componentCount;
		private int magFilter;
		private int minFilter;
		private int srcPixelFormat;
		private ByteBuffer textureBuffer;
		
		public int reload() {
			Log.error("Reloading texture: " + ref);
			return InternalTextureLoader.get().reload(TextureImpl.this, srcPixelFormat, componentCount, minFilter,
					magFilter, textureBuffer);
		}
	}
}
