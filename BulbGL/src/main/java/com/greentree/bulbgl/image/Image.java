package com.greentree.bulbgl.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.greentree.bulbgl.Color;
import com.greentree.bulbgl.image.util.ImageBuffer;
import com.greentree.bulbgl.opengl.rendener.Renderer;
import com.greentree.bulbgl.opengl.rendener.SGL;
import com.greentree.common.Log;

public class Image implements Serializable {
	
	public static final int BOTTOM_LEFT = 3;
	public static final int BOTTOM_RIGHT = 2;
	public static final int FILTER_LINEAR = 1;
	public static final int FILTER_NEAREST = 2;
	protected static SGL GL = Renderer.get();
	protected static Image inUse;
	private static final long serialVersionUID = 1L;
	public static final int TOP_LEFT = 0;
	public static final int TOP_RIGHT = 1;
	protected float alpha;
	protected float angle;
	protected float centerX;
	protected float centerY;
	protected Color[] corners;
	protected boolean destroyed;
	protected int height;
	protected boolean inited;
	protected String name;
	protected byte[] pixelData;
	protected String ref;
	protected Texture texture;
	protected float textureHeight;
	protected float textureOffsetX;
	protected float textureOffsetY;
	protected float textureWidth;
	protected int width;
	
	protected Image() {
		alpha = 1.0f;
		inited = false;
	}
	
	protected Image(final Image other) {
		alpha = 1.0f;
		inited = false;
		width = other.getWidth();
		height = other.getHeight();
		texture = other.texture;
		textureWidth = other.textureWidth;
		textureHeight = other.textureHeight;
		ref = other.ref;
		textureOffsetX = other.textureOffsetX;
		textureOffsetY = other.textureOffsetY;
		centerX = width / 2;
		centerY = height / 2;
		inited = true;
	}
	
	Image(final ImageBuffer buffer) {
		this(buffer, 1);
		TextureImpl.bindNone();
	}
	
	Image(final ImageBuffer buffer, final int filter) {
		this((ImageData) buffer, filter);
		TextureImpl.bindNone();
	}

	public Image(final ImageData data) {
		this(data, 1);
	}
	
	
	public Image(final ImageData data, final int f) {
		alpha = 1.0f;
		inited = false;
		try {
			int filter = getFilter(f);
			texture = InternalTextureLoader.get().getTexture(data, filter);
			ref = texture.toString();
		}catch(final IOException e) {
			Log.error(e);
		}
	}
	
	public Image(final InputStream in, final String ref, final boolean flipped) {
		this(in, ref, flipped, 1);
	}
	
	public Image(final InputStream in, final String ref, final boolean flipped, final int filter) {
		alpha = 1.0f;
		inited = false;
		load(in, ref, flipped, filter, null);
	}
	
	public Image(final int width, final int height) throws IOException {
		this(width, height, 2);
	}
	
	public Image(final int width, final int height, final int f) throws IOException {
		alpha = 1.0f;
		inited = false;
		ref = super.toString();
		int filter = getFilter(f);
		try {
			texture = InternalTextureLoader.get().createTexture(width, height, filter);
		}catch(final IOException e) {
			throw new IOException("Failed to create empty image " + width + "x" + height, e);
		}
		init();
	}
	
	private int getFilter(int f) {
		return f == 1 ? SGL.GL_LINEAR : SGL.GL_NEAREST;
	}

	public Image(final String ref) {
		this(ref, false, 1, null);
	}
	
	public Image(final String ref, final boolean flipped) {
		this(ref, flipped, 1, null);
	}
	
	public Image(final String ref, final boolean flipped, final int filter) {
		this(ref, flipped, filter, null);
	}
	
	public Image(final String ref, final boolean flipped, final int f, final Color transparent) {
		alpha = 1.0f;
		inited = false;
		int filter = getFilter(f);
		this.ref = ref;
		int[] trans = null;
		if(transparent != null) trans = new int[]{(int) (transparent.r * 255.0f),(int) (transparent.g * 255.0f),
				(int) (transparent.b * 255.0f)};
		try {
			texture = InternalTextureLoader.get().getTexture(ref, flipped, filter, trans);
		}catch(final IOException e) {
			Log.error("", e);
		}
	}
	
	public Image(final String ref, final Color trans) {
		this(ref, false, 1, trans);
	}
	
	public Image(final Texture texture) {
		alpha = 1.0f;
		inited = false;
		this.texture = texture;
		ref = texture.toString();
		clampTexture();
	}

	public void bind() {
		texture.bind();
	}
	
	public void clampTexture() {
		if(Image.GL.canTextureMirrorClamp()) {
			Image.GL.glTexParameteri(3553, 10242, 34627);
			Image.GL.glTexParameteri(3553, 10243, 34627);
		}else {
			Image.GL.glTexParameteri(3553, 10242, 10496);
			Image.GL.glTexParameteri(3553, 10243, 10496);
		}
	}
	
	public Image copy() {
		init();
		return getSubImage(0, 0, width, height);
	}
	
	public void destroy() {
		if(isDestroyed()) return;
		destroyed = true;
		texture.release();
	}
	
	public void draw() {
		this.draw(0.0f, 0.0f);
	}
	
	public void draw(final float x, final float y) {
		init();
		this.draw(x, y, width, height);
	}
	
	public void draw(final float x, final float y, final Color filter) {
		init();
		this.draw(x, y, width, height, filter);
	}
	
	public void draw(final float x, final float y, final float scale) {
		init();
		this.draw(x, y, width * scale, height * scale, Color.white);
	}
	
	public void draw(final float x, final float y, final float scale, final Color filter) {
		init();
		this.draw(x, y, width * scale, height * scale, filter);
	}
	
	public void draw(final float x, final float y, final float width, final float height) {
		init();
		this.draw(x, y, width, height, Color.white);
	}
	
	public void draw(final float x, final float y, final float width, final float height, Color filter) {
		if(alpha != 1.0f) {
			if(filter == null) filter = Color.white;
			final Color color;
			filter = color = new Color(filter);
			color.a *= alpha;
		}
		if(filter != null) filter.bind();
		texture.bind();
		Image.GL.glTranslatef(x, y, 0.0f);
		if(angle != 0.0f) {
			Image.GL.glTranslatef(centerX, centerY, 0.0f);
			Image.GL.glRotatef(angle, 0.0f, 0.0f, 1.0f);
			Image.GL.glTranslatef(-centerX, -centerY, 0.0f);
		}
		Image.GL.glBegin(7);
		drawEmbedded(0.0f, 0.0f, width, height);
		Image.GL.glEnd();
		if(angle != 0.0f) {
			Image.GL.glTranslatef(centerX, centerY, 0.0f);
			Image.GL.glRotatef(-angle, 0.0f, 0.0f, 1.0f);
			Image.GL.glTranslatef(-centerX, -centerY, 0.0f);
		}
		Image.GL.glTranslatef(-x, -y, 0.0f);
	}
	
	public void drawCentered(final float x, final float y) {
		this.draw(x - (getWidth() / 2), y - (getHeight() / 2));
	}
	
	public void drawEmbedded(final float x, final float y, final float width, final float height) {
		init();
		if(corners == null) {
			final float w = .5f / getWidth(), h = .5f / getHeight();
			Image.GL.glTexCoord2f(textureOffsetX + w, textureOffsetY + h);
			Image.GL.glVertex3f(x, y, 0.0f);
			Image.GL.glTexCoord2f(textureOffsetX + w, (textureOffsetY + textureHeight) - h);
			Image.GL.glVertex3f(x, y + height, 0.0f);
			Image.GL.glTexCoord2f((textureOffsetX + textureWidth) - w, (textureOffsetY + textureHeight) - h);
			Image.GL.glVertex3f(x + width, y + height, 0.0f);
			Image.GL.glTexCoord2f((textureOffsetX + textureWidth) - w, textureOffsetY + h);
			Image.GL.glVertex3f(x + width, y, 0.0f);
		}else {
			corners[0].bind();
			Image.GL.glTexCoord2f(textureOffsetX, textureOffsetY);
			Image.GL.glVertex3f(x, y, 0.0f);
			corners[3].bind();
			Image.GL.glTexCoord2f(textureOffsetX, textureOffsetY + textureHeight);
			Image.GL.glVertex3f(x, y + height, 0.0f);
			corners[2].bind();
			Image.GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY + textureHeight);
			Image.GL.glVertex3f(x + width, y + height, 0.0f);
			corners[1].bind();
			Image.GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY);
			Image.GL.glVertex3f(x + width, y, 0.0f);
		}
	}
	
	public void endUse() {
		if(Image.inUse != this) throw new RuntimeException("The sprite sheet is not currently in use");
		Image.inUse = null;
		Image.GL.glEnd();
	}
	
	public void ensureInverted() {
		if(textureHeight > 0.0f) {
			textureOffsetY += textureHeight;
			textureHeight = -textureHeight;
		}
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof Image) {
			final Image i = (Image) obj;
			return ref.equals(i.ref) && (textureHeight == i.textureHeight) && (textureOffsetX == i.textureOffsetX)
					&& (textureOffsetY == i.textureOffsetY) && (textureWidth == i.textureWidth) && (width == i.width)
					&& (height == i.height) && texture.equals(i.texture);
		}
		return true;
	}
	
	public void flushPixelData() {
		pixelData = null;
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public float getCenterOfRotationX() {
		init();
		return centerX;
	}
	
	public float getCenterOfRotationY() {
		init();
		return centerY;
	}
	
	public Color getColor(int x, int y) {
		if(pixelData == null) pixelData = texture.getTextureData();
		final int xo = (int) (textureOffsetX * texture.getTextureWidth());
		final int yo = (int) (textureOffsetY * texture.getTextureHeight());
		if(textureWidth < 0.0f) x = xo - x;
		else x += xo;
		if(textureHeight < 0.0f) y = yo - y;
		else y += yo;
		int offset = x + (y * texture.getTextureWidth());
		offset *= texture.hasAlpha() ? 4 : 3;
		if(texture.hasAlpha()) return new Color(translate(pixelData[offset]), translate(pixelData[offset + 1]),
				translate(pixelData[offset + 2]), translate(pixelData[offset + 3]));
		return new Color(translate(pixelData[offset]), translate(pixelData[offset + 1]),
				translate(pixelData[offset + 2]));
	}
	
	public Image getFlippedCopy(final boolean flipHorizontal, final boolean flipVertical) {
		init();
		final Image image = copy();
		if(flipHorizontal) {
			image.textureOffsetX = textureOffsetX + textureWidth;
			image.textureWidth = -textureWidth;
		}
		if(flipVertical) {
			image.textureOffsetY = textureOffsetY + textureHeight;
			image.textureHeight = -textureHeight;
		}
		return image;
	}
	
	public int getHeight() {
		init();
		return height;
	}
	
	public String getName() {
		return name;
	}
	
	public String getResourceReference() {
		return ref;
	}
	
	public float getRotation() {
		return angle;
	}
	
	public Image getScaledCopy(final float scale) {
		init();
		return this.getScaledCopy((int) (width * scale), (int) (height * scale));
	}
	
	public Image getScaledCopy(final int width, final int height) {
		init();
		final Image image = copy();
		image.width = width;
		image.height = height;
		image.centerX = width / 2;
		image.centerY = height / 2;
		return image;
	}
	
	public Image getSubImage(final int x, final int y, final int width, final int height) {
		init();
		final float newTextureOffsetX = ((x / (float) this.width) * textureWidth) + textureOffsetX;
		final float newTextureOffsetY = ((y / (float) this.height) * textureHeight) + textureOffsetY;
		final float newTextureWidth = (width / (float) this.width) * textureWidth;
		final float newTextureHeight = (height / (float) this.height) * textureHeight;
		final Image sub = new Image();
		sub.inited = true;
		sub.texture = texture;
		sub.textureOffsetX = newTextureOffsetX;
		sub.textureOffsetY = newTextureOffsetY;
		sub.textureWidth = newTextureWidth;
		sub.textureHeight = newTextureHeight;
		sub.width = width;
		sub.height = height;
		sub.ref = ref;
		sub.centerX = width / 2;
		sub.centerY = height / 2;
		return sub;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public float getTextureHeight() {
		init();
		return textureHeight;
	}
	
	public float getTextureOffsetX() {
		init();
		return textureOffsetX;
	}
	
	public float getTextureOffsetY() {
		init();
		return textureOffsetY;
	}
	
	public float getTextureWidth() {
		init();
		return textureWidth;
	}
	
	public int getWidth() {
		init();
		return width;
	}
	
	protected final void init() {
		if(inited) return;
		inited = true;
		if(texture != null) {
			width = texture.getImageWidth();
			height = texture.getImageHeight();
			textureOffsetX = 0.0f;
			textureOffsetY = 0.0f;
			textureWidth = texture.getWidth();
			textureHeight = texture.getHeight();
		}
		initImpl();
		centerX = width / 2;
		centerY = height / 2;
	}
	
	protected void initImpl() {
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	private void load(final InputStream in, final String ref, final boolean flipped, final int f,
			final Color transparent) {
		int filter = getFilter(f);
		try {
			this.ref = ref;
			int[] trans = null;
			if(transparent != null) trans = new int[]{(int) (transparent.r * 255.0f),(int) (transparent.g * 255.0f),
					(int) (transparent.b * 255.0f)};
			texture = InternalTextureLoader.get().getTexture(in, ref, flipped, filter, trans);
		}catch(final IOException e) {
			Log.error("Failed to load image from: " + ref, e);
		}
	}
	
	protected void reinit() {
		inited = false;
		init();
	}
	
	public void rotate(final float angle) {
		this.angle += angle;
		this.angle %= 360.0f;
	}
	
	public void setAlpha(final float alpha) {
		this.alpha = alpha;
	}
	
	public void setCenterOfRotation(final float x, final float y) {
		centerX = x;
		centerY = y;
	}
	
	public void setColor(final int corner, final float r, final float g, final float b) {
		if(corners == null) corners = new Color[]{new Color(1.0f, 1.0f, 1.0f, 1.0f),new Color(1.0f, 1.0f, 1.0f, 1.0f),
				new Color(1.0f, 1.0f, 1.0f, 1.0f),new Color(1.0f, 1.0f, 1.0f, 1.0f)};
		corners[corner].r = r;
		corners[corner].g = g;
		corners[corner].b = b;
	}
	
	public void setColor(final int corner, final float r, final float g, final float b, final float a) {
		if(corners == null) corners = new Color[]{new Color(1.0f, 1.0f, 1.0f, 1.0f),new Color(1.0f, 1.0f, 1.0f, 1.0f),
				new Color(1.0f, 1.0f, 1.0f, 1.0f),new Color(1.0f, 1.0f, 1.0f, 1.0f)};
		corners[corner].r = r;
		corners[corner].g = g;
		corners[corner].b = b;
		corners[corner].a = a;
	}
	
	public void setFilter(final int f) {
		int filter = getFilter(f);
		texture.bind();
		Image.GL.glTexParameteri(3553, 10241, filter);
		Image.GL.glTexParameteri(3553, 10240, filter);
	}
	
	public void setImageColor(final float r, final float g, final float b) {
		this.setColor(0, r, g, b);
		this.setColor(1, r, g, b);
		this.setColor(3, r, g, b);
		this.setColor(2, r, g, b);
	}
	
	public void setImageColor(final float r, final float g, final float b, final float a) {
		this.setColor(0, r, g, b, a);
		this.setColor(1, r, g, b, a);
		this.setColor(3, r, g, b, a);
		this.setColor(2, r, g, b, a);
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public void setRotation(final float angle) {
		this.angle = angle % 360.0f;
	}
	
	public void setTexture(final Texture texture) {
		this.texture = texture;
		reinit();
	}
	
	public void startUse() {
		if(Image.inUse != null) throw new RuntimeException(
				"Attempt to start use of a sprite sheet before ending use with another - see endUse()");
		(Image.inUse = this).init();
		Color.white.bind();
		texture.bind();
		Image.GL.glBegin(7);
	}
	
	@Override
	public String toString() {
		init();
		return "[Image " + ref + " " + width + "x" + height + "  " + textureOffsetX + "," + textureOffsetY + ","
		+ textureWidth + "," + textureHeight + "]";
	}
	
	private int translate(final byte b) {
		if(b < 0) return 256 + b;
		return b;
	}
}
