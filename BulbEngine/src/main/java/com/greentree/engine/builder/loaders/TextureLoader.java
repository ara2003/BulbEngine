package com.greentree.engine.builder.loaders;

import java.io.IOException;

import com.greentree.data.loaders.value.CachingAbstractLoader;
import com.greentree.graphics.texture.Filtering;
import com.greentree.graphics.texture.GLTexture2D;
import com.greentree.graphics.texture.GLTextureLoader;

/** @author Arseny Latyshev */
public class TextureLoader extends CachingAbstractLoader<GLTexture2D> {

	public TextureLoader() {
		super(GLTexture2D.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GLTexture2D load0(final String value) throws IOException {
		GLTexture2D t = GLTextureLoader.getTexture2D(value);
		t.setMinFilter(Filtering.NEAREST);
		t.setMagFilter(Filtering.LINEAR);
		return t;
	}
}
