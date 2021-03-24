package com.greentree.engine.editor.xml.loaders;

import java.io.IOException;

import com.greentree.bulbgl.image.InternalTextureLoader;
import com.greentree.bulbgl.image.Texture;
import com.greentree.bulbgl.opengl.rendener.SGL;
import com.greentree.engine.editor.xml.AbstractLoader;

/** @author Arseny Latyshev */
public class TextureLoader extends AbstractLoader<Texture> {
	
	public TextureLoader() {
		super(Texture.class);
	}
	
	@Override
	public Texture load(final String value)  {
		try {
			return InternalTextureLoader.get().getTexture(value, false, SGL.GL_NEAREST, null);
		}catch(final IOException e) {
		}
		return null;
	}
}
