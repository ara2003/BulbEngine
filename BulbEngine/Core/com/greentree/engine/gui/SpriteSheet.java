package com.greentree.engine.gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.greentree.opengl.Texture;

public class SpriteSheet extends Image {

	private static final long serialVersionUID = 1L;
	private int margin = 0;
	private int spacing;
	private Image[][] subImages;
	private final Image target;
	private final int th;
	private final int tw;

	public SpriteSheet(final Image image, final int tw, final int th) {
		super(image);
		target = image;
		this.tw = tw;
		this.th = th;
		initImpl();
	}

	public SpriteSheet(final Image image, final int tw, final int th, final int spacing) {
		this(image, tw, th, spacing, 0);
	}

	public SpriteSheet(final Image image, final int tw, final int th, final int spacing, final int margin) {
		super(image);
		target = image;
		this.tw = tw;
		this.th = th;
		this.spacing = spacing;
		this.margin = margin;
		initImpl();
	}

	public SpriteSheet(final String name, final InputStream ref, final int tw, final int th) {
		super(ref, name, false);
		target = this;
		this.tw = tw;
		this.th = th;
	}

	public SpriteSheet(final String ref, final int tw, final int th) {
		this(ref, tw, th, null);
	}

	public SpriteSheet(final String ref, final int tw, final int th, final Color col) {
		this(ref, tw, th, col, 0);
	}

	public SpriteSheet(final String ref, final int tw, final int th, final Color col, final int spacing) {
		super(ref, false, 2, col);
		target = this;
		this.tw = tw;
		this.th = th;
		this.spacing = spacing;
	}

	public SpriteSheet(final String ref, final int tw, final int th, final int spacing) {
		this(ref, tw, th, null, spacing);
	}

	public SpriteSheet(final URL ref, final int tw, final int th) throws IOException {
		this(new Image(ref.openStream(), ref.toString(), false), tw, th);
	}

	@Override
	public void endUse() {
		if(target == this) {
			super.endUse();
			return;
		}
		target.endUse();
	}

	public int getHorizontalCount() {
		target.init();
		initImpl();
		return subImages.length;
	}

	public Image getSprite(final int x, final int y) {
		target.init();
		initImpl();
		if(x < 0 || x >= subImages.length) throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
		if(y < 0 || y >= subImages[0].length)
			throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
		return target.getSubImage(x * (tw + spacing) + margin, y * (th + spacing) + margin, tw, th);
	}

	public Image getSubImage(final int x, final int y) {
		init();
		if(x < 0 || x >= subImages.length) throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
		if(y < 0 || y >= subImages[0].length)
			throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
		return subImages[x][y];
	}

	public int getVerticalCount() {
		target.init();
		initImpl();
		return subImages[0].length;
	}

	@Override
	protected void initImpl() {
		if(subImages != null) return;
		final int tilesAcross = (getWidth() - margin * 2 - tw) / (tw + spacing) + 1;
		int tilesDown = (getHeight() - margin * 2 - th) / (th + spacing) + 1;
		if((getHeight() - th) % (th + spacing) != 0) tilesDown++;
		subImages = new Image[tilesAcross][tilesDown];
		for(int x = 0; x < tilesAcross; x++) for(int y = 0; y < tilesDown; y++) subImages[x][y] = getSprite(x, y);
	}

	public void renderInUse(final int x, final int y, final int sx, final int sy) {
		subImages[sx][sy].drawEmbedded(x, y, tw, th);
	}

	@Override
	public void setTexture(final Texture texture) {
		if(target == this) {
			super.setTexture(texture);
			return;
		}
		target.setTexture(texture);
	}

	@Override
	public void startUse() {
		if(target == this) {
			super.startUse();
			return;
		}
		target.startUse();
	}
}
