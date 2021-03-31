package com.greentree.engine.editor.loaders;

import java.io.IOException;

import com.greentree.bulbgl.image.InternalTextureLoader;
import com.greentree.bulbgl.image.Texture;
import com.greentree.bulbgl.opengl.rendener.SGL;
import com.greentree.engine.core.editor.CachingAbstractLoader;

/** @author Arseny Latyshev */
public class TextureLoader extends CachingAbstractLoader<Texture> {
	
	public TextureLoader() {
		super(Texture.class);
	}
	
	@Override
	public Texture load0(final String value) throws IOException {
		return InternalTextureLoader.get().getTexture(value, false, SGL.GL_NEAREST, null);
	}
}
